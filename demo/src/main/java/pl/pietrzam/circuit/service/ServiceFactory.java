package pl.pietrzam.circuit.service;

import static java.lang.String.format;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ServiceFactory {

  private static final Map<ServiceType, Function<RestTemplate, PingService>> serviceTypeToServiceConstructor =
      Map.of(
          ServiceType.BAD, BadService::new,
          ServiceType.ERROR, ErrorService::new,
          ServiceType.GOOD, GoodService::new,
          ServiceType.MAYBE, MaybeService::new
      );

  private final RestTemplate restTemplate;
  private final CircuitBreakerRegistry cbRegistry;

  public PingService serviceFor(ServiceType type) {
    final PingService concreteService =
        serviceTypeToServiceConstructor.getOrDefault(
            type,
            rt -> {
              throw new IllegalStateException(format("Cannot find service for '%s'", type));
            }
        ).apply(restTemplate);

    final CircuitBreaker dummyCircuitBreaker = cbRegistry.circuitBreaker("dummyService");
    return new ServiceDecorator(concreteService, dummyCircuitBreaker);
  }

  @AllArgsConstructor
  private static class ServiceDecorator implements PingService {

    private final PingService concreteService;
    private final CircuitBreaker circuitBreaker;

    @Override
    public String ping() {
      var requestSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, concreteService::ping);
      return Try.ofSupplier(requestSupplier).get();
    }
  }
}
