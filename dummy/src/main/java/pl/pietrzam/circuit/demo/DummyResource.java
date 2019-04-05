package pl.pietrzam.circuit.demo;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ping", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DummyResource {

  @GetMapping("/good")
  @ResponseBody
  ResponseEntity pingGood() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/bad")
  @ResponseBody
  ResponseEntity pingBad() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/error")
  @ResponseBody
  ResponseEntity pingError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @GetMapping("/maybe")
  ResponseEntity pingMaybe() {
    boolean shouldFail = ThreadLocalRandom.current().nextBoolean();

    if (shouldFail) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } else {
      return ResponseEntity.status(HttpStatus.OK).build();
    }
  }

}
