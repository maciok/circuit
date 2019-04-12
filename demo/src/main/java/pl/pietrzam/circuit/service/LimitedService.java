package pl.pietrzam.circuit.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@CircuitBreaker(name = "dummyService")
@AllArgsConstructor
public class LimitedService implements PingService {

  private final RestTemplate restTemplate;

  @RateLimiter(name = "dummyLimiter")
  @Override
  public String ping() {
    restTemplate.exchange("/limited", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    return "OK!";
  }

}
