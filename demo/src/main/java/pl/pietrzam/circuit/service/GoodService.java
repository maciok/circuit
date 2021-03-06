package pl.pietrzam.circuit.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@CircuitBreaker(name = "dummyService")
@AllArgsConstructor
public class GoodService implements PingService {

  private final RestTemplate restTemplate;

  @Override
  public String ping() {
    restTemplate.exchange("/good", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    return "OK!";
  }

}
