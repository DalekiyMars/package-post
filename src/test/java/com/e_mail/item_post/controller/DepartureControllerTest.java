package com.e_mail.item_post.controller;

import com.e_mail.item_post.config.JacksonConfig;
import com.e_mail.item_post.dto.DepartureDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

@SpringBootTest
public class DepartureControllerTest {
    @Autowired
    private DepartureController departureController;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BindingResult bindingResult;

    private final String DEPARTURE_DTO_PATH = "resources/controller/registerDepartureDto.json";

    @Test
    void registerDepartureTest() throws JsonProcessingException {
        var departureDto = objectMapper.readValue(DEPARTURE_DTO_PATH, DepartureDto.class);
        var res = departureController.registerDeparture(departureDto, bindingResult);

    }
}
