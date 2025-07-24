package com.frg.autocare.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record ApplicationProperties(String dummyUserPassword) {}
