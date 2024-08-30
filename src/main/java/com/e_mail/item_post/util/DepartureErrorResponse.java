package com.e_mail.item_post.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartureErrorResponse {
    private String message;
    private long timestamp;
}
