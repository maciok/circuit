package pl.pietrzam.circuit.demo;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TimeConfiguration {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }
}
