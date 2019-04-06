package pl.pietrzam.circuit.service;

import io.github.resilience4j.cache.Cache;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@CircuitBreaker(name = "dummyService")
@AllArgsConstructor
public class MaybeService {

  private final RestTemplate restTemplate;
  private final Cache<Void, String> cache;
  
  public String ping() {
    restTemplate.exchange("/maybe", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    return "OK!";
  }
  
  @Retry(name = "maybeRetrier")
  public String pingWithRetry() {
    return ping();
  }
  
}
