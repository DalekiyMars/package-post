package com.e_mail.item_post.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class PostRequestExceptionHandler {
    public String generateMessageAboutErrors(BindingResult result){
        StringBuilder errmsg = new StringBuilder();
        result.getFieldErrors().forEach(s -> errmsg.append(s.getField())
                .append(" - ")
                .append(s.getDefaultMessage())
                .append("; "));

        return errmsg.toString();

    }
}
