package com.e_mail.item_post.controller;

import com.e_mail.item_post.common.Departure;
import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.util.DepartureErrorResponse;
import com.e_mail.item_post.util.DepartureNotCreatedException;
import com.e_mail.item_post.util.DepartureNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.e_mail.item_post.constants.Constants.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/departures")
public class DepartureController {
    private final DepartureService departureService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping()
    public List<Departure> getAllDepartures(){
        return departureService.findAll();
    }
    @GetMapping("/{id}")
    public Departure getDeparture(@PathVariable("id") int id){
        return departureService.findOne(id);
    }

    @PostMapping
    private ResponseEntity<HttpStatus> registerDeparture(@RequestBody @Valid Departure departure,
                                                         BindingResult result){
        if (result.hasErrors()){
            StringBuilder errmsg = new StringBuilder();
            result.getFieldErrors().forEach(s -> errmsg.append(s.getField())
                                                            .append(" - ")
                                                            .append(s.getDefaultMessage())
                                                            .append("; "));

            throw new DepartureNotCreatedException(errmsg.toString());
        }
        departureService.save(departure);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DepartureErrorResponse> departureIsNotFound(DepartureNotFoundException e){
        DepartureErrorResponse response = new DepartureErrorResponse(
                ExceptionMessages.INCORRECT_DEPARTURE,
                System.currentTimeMillis()
        );
        log.error("Пользователь не найден в " + sdf.format(response.getTimestamp()) + " - неверный ID");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<DepartureErrorResponse> departureIsNotCreated(DepartureNotCreatedException e){
        DepartureErrorResponse response = new DepartureErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Пользователь не cоздан в  " + sdf.format(response.getTimestamp()) + " - недопустимые данные");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
