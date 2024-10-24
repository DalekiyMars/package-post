package com.e_mail.item_post.db.service;

import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;


@SpringBootTest
@Testcontainers
class PostServiceTest {

    @Autowired
    PostService service;

    @Autowired
    ModelMapper mapper;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    final String BASE_ENTITY_PATH = "src/test/resources/controller";

    static {
        postgreSQLContainer.start();
    }

    @Test
    void savePostTest() throws IOException {
        var post = JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerPostDto.json", Post.class);
        var savedPost = service.save(post);
        Assertions.assertEquals(post, savedPost);
    }

    @Test
    void getAllPosts() {

    }

    @Test
    void getPostById() {
    }

    @Test
    void searchPost() {
    }

    @Test
    void delete() {
    }
}