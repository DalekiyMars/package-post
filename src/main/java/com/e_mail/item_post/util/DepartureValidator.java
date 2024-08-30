package com.e_mail.item_post.util;

import com.e_mail.item_post.common.Departure;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DepartureValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Departure.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Departure departure = (Departure) target;
    }
}
