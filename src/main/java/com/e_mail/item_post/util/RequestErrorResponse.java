package com.e_mail.item_post.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestErrorResponse {
    private String message;
    private long timestamp;

    public String getDateTime(){
        var data = new DataFormatNormalize();
        return data.MillisToDateTime(timestamp);
    }
}
