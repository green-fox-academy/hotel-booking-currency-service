package com.hiddenite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationToRestTemplate {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
