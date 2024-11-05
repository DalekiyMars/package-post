package com.e_mail.item_post.controller;

import com.e_mail.item_post.util.DtoBadRequestException;
import com.e_mail.item_post.util.ExceptionMessageCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionApiHandler {
    private final ExceptionMessageCreator exceptionMessageCreator;

    @ExceptionHandler(DtoBadRequestException.class)
    @ResponseStatus
    public ResponseEntity<ErrorMessage> incorrectRequestFormat(DtoBadRequestException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus
    public ResponseEntity<ErrorMessage> onConstraintValidationException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionMessageCreator.getExceptionMessages(exception));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus
    public ResponseEntity<ErrorMessage> onConstraintValidationException(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public ResponseEntity<ErrorMessage> unknownException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
