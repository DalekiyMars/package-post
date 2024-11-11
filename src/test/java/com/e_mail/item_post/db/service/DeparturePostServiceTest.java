package com.e_mail.item_post.db.service;

import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.entity.DeparturePost;
import com.e_mail.item_post.util.DtoBadRequestException;
import com.e_mail.item_post.util.JsonUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DeparturePostServiceTest {
    @Autowired
    DeparturePostService departurePostService;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    static {
        postgreSQLContainer.start();
    }

    @Autowired
    ModelMapper mapper;

    final String BASE_ENTITY_PATH = "src/test/resources/controller/departurePostEntities";

    DeparturePost getTestDeparturePost() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/DeparturePost.json", DeparturePost.class);
    }

    DeparturePostDto getTestDeparturePostDto() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerDeparturePostDto.json", DeparturePostDto.class);
    }

    DeparturePostDto getTestDeparturePostDtoWithException() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerDeparturePostDtoWithException.json", DeparturePostDto.class);
    }


    @Test
    void searchDepartureAndPost_EntityAcceptable() throws IOException {
        var answer = departurePostService.searchDepartureAndPost(getTestDeparturePostDto());
        var testEntity = getTestDeparturePost();
        Assertions.assertThat(answer.getDeparture()).isEqualTo(testEntity.getDeparture());
        Assertions.assertThat(answer.getPost()).isEqualTo(testEntity.getPost());
        Assertions.assertThat(answer.getStatus()).isEqualTo(testEntity.getStatus());
    }

    @Test
    void searchDepartureAndPost_EntityNotAcceptable(){
        assertThrows(DtoBadRequestException.class, () -> departurePostService.searchDepartureAndPost(getTestDeparturePostDtoWithException()));
    }

    @Transactional
    @Test
    void saveDepartureAndUpdatedPost_EntityAcceptable() throws IOException {
        var takenEntity = departurePostService.saveDepartureAndUpdatedPost(getTestDeparturePostDto());
        Assertions.assertThat(takenEntity)
                .isEqualTo(getTestDeparturePost());
    }

    @Test
    void saveDepartureAndUpdatedPost_EntityNotAcceptable(){
        assertThrows(DtoBadRequestException.class, () -> departurePostService.saveDepartureAndUpdatedPost(getTestDeparturePostDtoWithException()));
    }

    @Disabled //FIXME Проблема с lazy загрузкой ссылочных данных
    @Test
    void getHistoryAboutDeparture_EntityExists() throws IOException {
        var takenEntity = departurePostService.getHistoryAboutDeparture(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e21"));
        var testEntity = JsonUtils.convertListToJson(takenEntity);
        JsonAssertions.assertThatJson(testEntity).isEqualTo(getTestDeparturePostDto());
    }

    @Test
    void getHistoryAboutDeparture_EntityNotExists() {
        assertThrows(DtoBadRequestException.class,
                () -> departurePostService.getHistoryAboutDeparture(UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
    }

    @Transactional
    @Test
    void deleteDepartureAndPostHistory_EntityExists() {
        assertDoesNotThrow(() -> departurePostService.deleteDepartureAndPostHistory(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e21")));
    }

    @Test
    void deleteDepartureAndPostHistory_EntityNotExists() {
        assertThrows(DtoBadRequestException.class,
                () -> departurePostService.deleteDepartureAndPostHistory(UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
    }
}
