package com.e_mail.item_post.db.service;

import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.entity.Departure;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest
@Testcontainers
class DepartureServiceTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    DepartureService service;

    @Autowired
    ModelMapper mapper;

    final String BASE_ENTITY_PATH = "src/test/resources/controller/departureEntities";


    static {
        postgreSQLContainer.start();
    }

    Departure getTestDeparture() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/testDeparture.json", Departure.class);
    }

    DepartureDto getCorrectDepartureDto() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerDepartureDto.json", DepartureDto.class);
    }

    DepartureDto getWrongDepartureDto() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "/registerDepartureDtoWithMistake.json", DepartureDto.class);
    }

    @Test
    void findAll() throws IOException {
        var jsonWithDeparture = JsonUtils.convertListToJson(service.findAll().stream()
                .map(departure -> mapper.map(departure, DepartureDto.class))
                .collect(Collectors.toList()));

        JsonAssertions.assertThatJson(jsonWithDeparture).isEqualTo(JsonUtils.readJsonToString("src/test/resources/controller/departureEntities/testDepartureList.json"));
    }

    @Test
    void findOne_DepartureExists() throws IOException {
        var testDeparture = getTestDeparture();
        var takenDeparture = service.findOne(testDeparture.getId());
        Assertions.assertEquals(takenDeparture, testDeparture);
    }

    @Test
    void findOne_DepartureNotExists() throws IOException {
        var testDeparture = getTestDeparture();
        var takenDeparture = service.findOne(testDeparture.getId());
        Assertions.assertEquals(takenDeparture, testDeparture);
    }

    @Test
    void save_DepartureCorrect() throws IOException {
        var correctDeparture = getCorrectDepartureDto();
        var savedDeparture = service.save(mapper.map(correctDeparture, Departure.class));
        JsonAssertions.assertThatJson(JsonUtils.convertJsonFromObjectToString(mapper.map(savedDeparture, DepartureDto.class)))
                      .isEqualTo(JsonUtils.convertJsonFromObjectToString(correctDeparture));
    }

    @Test
    void save_DepartureIncorrect() {
        assertThrows(DataIntegrityViolationException.class, () -> service.save(mapper.map(getWrongDepartureDto(), Departure.class)));
    }

    @Test
    @Rollback
    void updateDepartureInfo_DepartureExistsAndCorrect() throws IOException {
        var testDeparture = getTestDeparture();
        Assertions.assertNotEquals(service.findOne(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24")), testDeparture);
        service.updateDepartureInfo(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24"), testDeparture);
        Assertions.assertEquals(testDeparture, service.findOne(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24")));
    }

    @Test
    void updateDepartureInfo_DepartureNotExists() {
        assertThrows(DtoBadRequestException.class, () -> service.updateDepartureInfo(UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA"), getTestDeparture()));
    }

    @Test
    void updateDepartureInfo_DepartureNotCorrect() {
        assertThrows(DataIntegrityViolationException.class,
                () -> service.updateDepartureInfo(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24"), mapper.map(getWrongDepartureDto(), Departure.class)));
    }

    @Test
    void delete_EntityWithThisIdNotExists() {
        assertThrows(DtoBadRequestException.class, () -> service.delete(UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
    }

    @Test
    void delete_EntityWithThisIdExists() {
        Assertions.assertDoesNotThrow(() -> service.findOne(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24")));
        service.delete(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24"));
        assertThrows(DtoBadRequestException.class, () -> service.findOne(UUID.fromString("ea901f00-ecfe-4bfc-9b35-b9e0356d3e24")));
    }
}