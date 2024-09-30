package com.e_mail.item_post.controller;

import com.e_mail.item_post.util.DtoBadRequestException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(DtoBadRequestException.class)
    public ResponseEntity<ErrorMessage> incorrectRequestFormat(DtoBadRequestException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
