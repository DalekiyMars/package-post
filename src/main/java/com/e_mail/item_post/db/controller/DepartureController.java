package com.e_mail.item_post.db.controller;

import com.e_mail.item_post.db.service.DepartureService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class DepartureController {
    private final DepartureService departureService;
}
