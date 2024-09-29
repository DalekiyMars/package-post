package com.e_mail.item_post.controller;

import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.util.RequestErrorResponse;
import com.e_mail.item_post.util.DtoBadRequestException;
import com.e_mail.item_post.util.PostRequestExceptionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/departures")
public class DepartureController {
    private final DepartureService departureService;
    private final ModelMapper modelMapper;
    private final PostRequestExceptionHandler exceptionHandler;

    @GetMapping()
    public List<DepartureDto> getAllDepartures() {
        return departureService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DepartureDto getDeparture(@PathVariable("id") int id) {
        return modelMapper.map(departureService.findOne(id), DepartureDto.class);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> registerDeparture(@RequestBody @Valid DepartureDto departureDTO,
                                                        BindingResult result) throws DtoBadRequestException {
        if (result.hasErrors()){
            throw new DtoBadRequestException(exceptionHandler.generateMessageAboutErrors(result));
        }

        var temp = departureService.save(modelMapper.map(departureDTO, Departure.class));
        log.info("Departure с id " + temp.getId() + "  сохранен");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateDataAboutCurrentDeparture(@PathVariable("id") int id, @RequestBody @Valid DepartureDto departureDto){
        if (departureService.updateDepartureInfo(id, modelMapper.map(departureDto, Departure.class))){
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler
    private ResponseEntity<RequestErrorResponse> departureError(DtoBadRequestException e) {
        RequestErrorResponse response = new RequestErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Пользователь не cоздан в  " + response.getDateTime() + " - недопустимые данные");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public DepartureDto convertToDTO(Departure departure) {
        return modelMapper.map(departure, DepartureDto.class);
    }
}
