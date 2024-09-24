package com.e_mail.item_post.controller;

import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.util.DataFormatNormalize;
import com.e_mail.item_post.util.DepartureErrorResponse;
import com.e_mail.item_post.util.ItemPostException;
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
    private final DataFormatNormalize data;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<DepartureDto> getAllDepartures() {
        return departureService.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DepartureDto getDeparture(@PathVariable("id") int id) {
        return convertToDTO(departureService.findOne(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> registerDeparture(@RequestBody @Valid DepartureDto departureDTO,
                                                        BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errmsg = new StringBuilder();
            result.getFieldErrors().forEach(s -> errmsg.append(s.getField())
                    .append(" - ")
                    .append(s.getDefaultMessage())
                    .append("; "));

            throw new ItemPostException(errmsg.toString());
        }
        var temp = departureService.save(convertToDeparture(departureDTO));
        log.info("Departure с id " + temp.getId() + "  сохранен");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DepartureErrorResponse> departureError(ItemPostException e) {
        DepartureErrorResponse response = new DepartureErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Пользователь не cоздан в  " + data.MillisToDateTime(response.getTimestamp()) + " - недопустимые данные");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public Departure convertToDeparture(DepartureDto dto) {
        return modelMapper.map(dto, Departure.class);
    }

    public DepartureDto convertToDTO(Departure departure) {
        return modelMapper.map(departure, DepartureDto.class);
    }
}
