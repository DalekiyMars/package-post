package com.e_mail.item_post.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private final Environment env;

    public SwaggerConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080")
                        )
                )
                .info(
                        new Info()
                                .title("Items and posts API")
                                .description("Manual how to use API")
                                .version(env.getProperty("application-version"))
                );
    }
}
