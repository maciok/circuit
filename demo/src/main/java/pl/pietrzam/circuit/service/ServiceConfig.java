package pl.pietrzam.circuit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ServiceConfig {

  @Bean
  RestTemplate restTemplate(@Value("${dummy.service.url}") String dummyUrl) {
    return new RestTemplateBuilder()
        .rootUri(dummyUrl)
        .build();
  }

}
