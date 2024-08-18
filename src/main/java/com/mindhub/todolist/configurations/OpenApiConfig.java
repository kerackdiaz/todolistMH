package com.mindhub.todolist.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")

@OpenAPIDefinition(
        info = @Info(
                title = "To Do List Documentation",
                version = "0.1.2",
                description = "This is a simple To Do List application",
                contact = @Contact(
                        name = "Kerack Diaz",
                        email = "kerackdarcku@gmail.com"

                ))
)
public class OpenApiConfig {
}

