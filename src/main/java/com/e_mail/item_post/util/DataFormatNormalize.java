package com.e_mail.item_post.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class DataFormatNormalize {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String MillisToDateTime(long millis) {
        return sdf.format(millis);
    }
}
