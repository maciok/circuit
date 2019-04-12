package pl.pietrzam.circuit.demo;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ping", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class DummyResource {
  
  private final LimitedService limited;

  @GetMapping("/good")
  @ResponseBody
  ResponseEntity pingGood() {
    log.info("Invoking good");
    return ResponseEntity.ok().build();
  }

  @GetMapping("/bad")
  @ResponseBody
  ResponseEntity pingBad() {
    log.info("Invoking bad");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/error")
  @ResponseBody
  ResponseEntity pingError() {
    log.info("Invoking error");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @GetMapping("/maybe")
  ResponseEntity pingMaybe() {
    log.info("Invoking maybe");
    boolean shouldFail = ThreadLocalRandom.current().nextBoolean();

    if (shouldFail) {
      log.info("It'll be error");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } else {
      log.info("It'll be ok");
      return ResponseEntity.status(HttpStatus.OK).build();
    }
  }

  @GetMapping("/limited")
  @ResponseBody
  ResponseEntity pingLimited() {
    log.info("Invoking limited");
    
    if(limited.tryExecute()) {
      log.info("It's just right");
      return ResponseEntity.status(HttpStatus.OK).body("just right");
    } else {
      log.info("Too frequently");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("too frequently");
    }
  }


}
