package com.e_mail.item_post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращаем имя HTML-шаблона для страницы входа (login.html)
    }
}
