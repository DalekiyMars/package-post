package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.entity.Departure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping()
    public List<DepartureDto> getAllDepartures() {
        return departureService.findAll().stream()
                .map(departure ->  modelMapper.map(departure, DepartureDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DepartureDto getDeparture(@PathVariable("id") int id) {
        return modelMapper.map(departureService.findOne(id), DepartureDto.class);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> registerDeparture(@RequestBody @Validated DepartureDto departureDTO) {
        var temp = departureService.save(modelMapper.map(departureDTO, Departure.class));
        log.info("Departure с id " + temp.getId() + "  сохранен");
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateDataAboutCurrentDeparture(@PathVariable("id") int id, @RequestBody DepartureDto departureDto){
        departureService.updateDepartureInfo(id, modelMapper.map(departureDto, Departure.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDeparture(@PathVariable("id") int id){
        departureService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
