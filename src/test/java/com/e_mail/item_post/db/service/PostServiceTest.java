package com.e_mail.item_post.db.service;

import com.e_mail.item_post.dto.PostDto;
import com.e_mail.item_post.entity.Post;
import com.e_mail.item_post.util.DtoBadRequestException;
import com.e_mail.item_post.util.JsonUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.stream.Collectors;

@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
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

    final String BASE_ENTITY_PATH = "src/test/resources/controller/postEntities";

    static {
        postgreSQLContainer.start();
    }

    PostDto getCorrectPostDto() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerPostDto.json", PostDto.class);
    }

    Post getTestPost() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/responsePost.json", Post.class);
    }

    PostDto getWrongPotDto() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerPostDtoWithMistake.json", PostDto.class);
    }

    @Test
    void savePostTest_PostInfoAcceptable() throws IOException {
        var post = getCorrectPostDto();
        var savedPost = mapper.map(service.save(post), PostDto.class);

        Assertions.assertEquals(post, savedPost);
    }

    @Test
    void savePostTest_PostInfoUnacceptable() throws IOException {
        var post = getWrongPotDto();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.save(post));
    }

    @Test
    void getAllPosts() throws IOException {
        var jsonWithPosts = JsonUtils.convertListToJson(service.getAllPosts().stream()
                .map(post -> mapper.map(post, PostDto.class))
                .collect(Collectors.toList()));
        JsonAssertions.assertThatJson(jsonWithPosts).isEqualTo(JsonUtils.readJsonToString(BASE_ENTITY_PATH + "/responsePostList.json"));
    }

    @Test
    void getPostById_PostExists() throws IOException {
        var testPost = getTestPost();
        var exceptedPost = service.getPostById(testPost.getId());
        Assertions.assertEquals(testPost, exceptedPost);
    }

    @Test
    void getPostById_PostNotExists() {
        Assertions.assertThrows(DtoBadRequestException.class, () -> service.getPostById(100L));
    }

    @Test
    void delete_whenPostExists() throws IOException {
        var testPost = getTestPost();
        service.delete(testPost.getId());
        Assertions.assertThrows(DtoBadRequestException.class, () -> service.getPostById(testPost.getId()));
    }

    @Test
    void delete_whenPostNotExists() {
        Assertions.assertThrows(DtoBadRequestException.class, () -> service.delete(100L));
    }

}