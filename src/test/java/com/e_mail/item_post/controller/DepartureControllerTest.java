package com.e_mail.item_post.controller;

import com.e_mail.item_post.dto.DepartureDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepartureControllerTest {
    @Autowired
    private DepartureController departureController;

    @Autowired
    private ObjectMapper objectMapper;
    private DepartureDto departureDto;

    @BeforeEach
    void deserializeDepartureDto() throws JsonProcessingException {
        String DEPARTURE_DTO_PATH = "resources/controller/registerDepartureDto.json";
        departureDto = objectMapper.readValue(DEPARTURE_DTO_PATH, DepartureDto.class);
    }

    @Test
    void registerDepartureTest_departureRegistered(){
        var res = departureController.registerDeparture(departureDto);

    }

    @Test
    void registerDepartureTest_departureNotRegistered(){
        var res = departureController.registerDeparture(departureDto);
        assertEquals(ResponseEntity.ok(HttpStatus.OK), res);
    }
}
