package com.e_mail.item_post.controller;

import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.util.JsonUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DepartureControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    final String BASE_ENTITY_PATH = "src/test/resources/controller/departureEntities";
    final String BASE_URL_PATH = "/departures";

    static {
        postgreSQLContainer.start();
    }

    @Test
    void getAllDepartures() throws Exception {
        var answer = this.mockMvc.perform(get(BASE_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var takenDepartures = JsonUtils.convertJsonStringToObjectList(answer.getResponse().getContentAsString(), DepartureDto.class);
        var exceptedDepartures = JsonUtils.convertJsonStringToObjectList(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/testDepartureList.json"), DepartureDto.class);
        JsonAssertions.assertThatJson(takenDepartures).isEqualTo(exceptedDepartures);
    }

    @Test
    void getDeparture_DepartureExists() throws Exception {
        var departure = JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/registerDepartureDto.json");
        var answer = this.mockMvc.perform(get(BASE_URL_PATH + "/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(JsonUtils.convertJsonStringToObject(departure, DepartureDto.class),
                JsonUtils.convertJsonStringToObject(answer.getResponse().getContentAsString(), DepartureDto.class));
    }

    @Test
    void getDeparture_DepartureNotExists() throws Exception {
        var answer = this.mockMvc.perform(get(BASE_URL_PATH + "/fa901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/responseDepartureNotExists.json"));
    }

    @Test
    void registerDeparture_DepartureAcceptable() throws Exception {
        var departure = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerDepartureDto.json", DepartureDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departure))
                .andExpect(status().isOk());
    }

    @Test
    void registerDeparture_DepartureUnacceptable() throws Exception {
        var departure = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerDepartureDtoWithMistake.json", DepartureDto.class);
        var answer = this.mockMvc.perform(post(BASE_URL_PATH + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departure))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/responseExceptionName.json"));
    }

    @Test
    void updateDataAboutCurrentDeparture_DepartureAcceptable() throws Exception {
        var departure = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerDepartureDto.json", DepartureDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/update/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departure))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateDataAboutCurrentDeparture_DepartureUnacceptable() throws Exception {
        var departure = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerDepartureDtoWithMistake.json", DepartureDto.class);
        var answer = this.mockMvc.perform(post(BASE_URL_PATH + "/update/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departure))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/responseExceptionName.json"));
    }

    @Test
    void deleteDeparture_DepartureNotExists() throws Exception {
        var answer = this.mockMvc.perform(delete(BASE_URL_PATH + "/delete/fa901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JsonAssertions.assertThatJson(answer.getResponse().getContentAsString()).isEqualTo(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/responseDepartureNotExists.json"));
    }

    @Transactional
    @Test
    void deleteDeparture_DepartureExists() throws Exception {
        this.mockMvc.perform(delete(BASE_URL_PATH + "/delete/ea901f00-ecfe-4bfc-9b35-b9e0356d3e21")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
