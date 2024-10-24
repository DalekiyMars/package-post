package com.e_mail.item_post.controller;

import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class MailPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    final String BASE_ENTITY_PATH = "src/test/resources/controller";
    final String BASE_URL_PATH = "/posts";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    static {
        postgreSQLContainer.start();
    }

    @Test
    void addNewPost_RealPost() throws Exception {
        var post = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerPostDto.json", PostDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(post))
                .andExpect(status().isOk());
    }

    @Test
    void addNewPost_BrokenPostInfo() throws Exception {
        var post = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerPostDtoWithMistake.json", PostDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(post))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void getPostById_PostExists() throws Exception {
        this.mockMvc.perform(get(BASE_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPostById_PostNotExists() throws Exception {
        this.mockMvc.perform(get(BASE_URL_PATH + "/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updatePostInfo_PostExists() throws Exception {
        var post = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerPostDto.json", PostDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(post))
                .andExpect(status().isOk());
    }

    @Test
    void updatePostInfo_PostNotExists() throws Exception {
        var post = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerPostDto.json", PostDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/update/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(post))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updatePostInfo_NewPostHasMistakes() throws Exception {
        var post = JsonUtils.convertJsonFromFileToString(BASE_ENTITY_PATH + "/registerPostDtoWithMistake.json", PostDto.class);
        this.mockMvc.perform(post(BASE_URL_PATH + "/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(post))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getMailPosts() throws Exception {
        this.mockMvc.perform(get(BASE_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].index").isString())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].ownerAddress").isString());
    }

    @Test
    void deletePost_PostExists() throws Exception {
        this.mockMvc.perform(delete(BASE_URL_PATH + "/delete/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deletePost_PostNotExists() throws Exception {
        this.mockMvc.perform(delete(BASE_URL_PATH + "/delete/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}