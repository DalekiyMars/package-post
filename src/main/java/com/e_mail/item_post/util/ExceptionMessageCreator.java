package com.e_mail.item_post.util;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

@Component
public class ExceptionMessageCreator {
    public ErrorMessage getExceptionMessages(BindException exception){
        return new ErrorMessage(exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }
}
