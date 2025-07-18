package com.frg.autocare.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ApplicationProperties {
  @Value("${dummy.password}")
  private String DUMMY_ADMIN_USER_PASSWORD;
}
