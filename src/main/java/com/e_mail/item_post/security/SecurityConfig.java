package com.e_mail.item_post.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/api-docs/**")) // Отключаем CSRF для public API
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts", "/api-docs", "/login").permitAll() // Доступ без авторизации
                        .anyRequest().authenticated()                                    // Требуется авторизация для остальных запросов
                ).formLogin(form -> form
                    .loginPage("/login")                                                 // Указываем путь к странице входа
                    .permitAll()                                                         // Разрешаем доступ к странице входа без авторизации
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                                            // URL для выхода
                .logoutSuccessUrl("/login?logout")                                       // URL после успешного выхода
                        .permitAll()                                                     // Разрешаем доступ к выходу без авторизации
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Хэширование паролей
    }
}
