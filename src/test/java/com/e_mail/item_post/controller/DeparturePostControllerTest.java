package com.e_mail.item_post.controller;

import com.e_mail.item_post.util.JsonUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class DeparturePostControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    MockMvc mvc;

    @Autowired
    ModelMapper mapper;

    final String BASE_ENTITY_PATH = "src/test/resources/controller/departurePostEntities";
    final String BASE_URL_PATH = "/update-status";

    static {
        postgreSQLContainer.start();
    }

    String getDeparturePostDtoString() throws IOException {
        return JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/registerDeparturePostDto.json");
    }

    String getDeparturePostDtoWithMistakeString() throws IOException {
        return JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/registerDeparturePostDtoWithMistake.json");
    }

    String getExceptionAboutStatusString() throws IOException {
        return JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/StatusIsEmptyException.json");
    }

    String getInfoAboutEntityByIdString() throws IOException {
        return JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/DeparturePostResponseAboutCurrentId.json");
    }

    String getEntityNotExistsExceptionString() throws IOException {
        return JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/EntityNotExistsException.json");
    }

    @Transactional
    @Test
    void saveDeparturePost_EntityAcceptable() throws Exception {
        this.mvc.perform(post(BASE_URL_PATH + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDeparturePostDtoString()))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void saveDeparturePost_EntityUnacceptable() throws Exception {
        var answer = this.mvc.perform(post(BASE_URL_PATH + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDeparturePostDtoWithMistakeString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        var takenExceptionJson = answer.getResponse().getContentAsString();
        JsonAssertions.assertThatJson(takenExceptionJson).isEqualTo(getExceptionAboutStatusString());
    }

    @Test
    void searchDeparturePost_EntityExists() throws Exception {
        var answer = this.mvc.perform(get(BASE_URL_PATH + "/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21"))
                .andExpect(status().isOk())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString())
                .whenIgnoringPaths("$.[*].whenArrived")
                .isEqualTo(getInfoAboutEntityByIdString());
    }


    @Test
    void searchDeparturePost_EntityNotExists() throws Exception {
        var answer = this.mvc.perform(get(BASE_URL_PATH + "/AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(getEntityNotExistsExceptionString());
    }

    @Transactional
    @Test
    void deleteCurrentHistory_EntityExists() throws Exception {
        this.mvc.perform(delete(BASE_URL_PATH + "/deleteHistory/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21"))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void deleteCurrentHistory_EntityNotExists() throws Exception {
        var answer = this.mvc.perform(delete(BASE_URL_PATH + "/deleteHistory/AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(getEntityNotExistsExceptionString());
    }
}