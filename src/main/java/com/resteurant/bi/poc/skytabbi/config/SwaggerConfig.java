package com.resteurant.bi.poc.skytabbi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("BI test")
            .description("BI test")
            .version("v0.0.2")
            .contact(new Contact().name("Tomasz Stepien"))
            .license(new License().name("")));
    }
}