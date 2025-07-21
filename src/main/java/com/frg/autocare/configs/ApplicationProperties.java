package com.frg.autocare.configs;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "autocare-rest-api")
public record ApplicationProperties(
    @NotEmpty(message = "password must not be empty") String dummyUserPassword) {}
