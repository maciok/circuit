package pl.pietrzam.circuit.demo;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;

@Service
class LimitedService {

  private static final Lock lock = new ReentrantLock();
  private static final int EXECUTION_LIMIT = 4;
  private static final TemporalAmount EXECUTION_LIMIT_PERIOD = Duration.of(1, SECONDS);

  private final Clock clock;

  private volatile LocalDateTime executionTime;
  private volatile AtomicInteger accesses;

  LimitedService(Clock clock) {
    this.clock = clock;
    this.executionTime = LocalDateTime.now(clock);
    this.accesses = new AtomicInteger(0);
  }

  boolean tryExecute() {
    lock.lock();
    boolean wasExecuted = execute(LocalDateTime.now(clock));
    lock.unlock();

    return wasExecuted;
  }

  private boolean execute(LocalDateTime now) {
    if (executionTime.plus(EXECUTION_LIMIT_PERIOD).isBefore(now)) {
      reset(now);
      return true;
    } else {
      int times = accesses.incrementAndGet();
      return times <= EXECUTION_LIMIT;
    }
  }

  private void reset(LocalDateTime actualExecutionTime) {
    executionTime = actualExecutionTime;
    accesses.set(1);
  }

}
