package com.example.neoflex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.neoflex.controller"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(getApiInfo());

    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Neoflex Vacation calendar",
                "Vacation calendar API.",
                "0.1",
                "Terms of service",
                new Contact(
                        "Nikolay Koziakov",
                        "https://t.me/Jimbanani",
                        "uservice371@mail.ru"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
}