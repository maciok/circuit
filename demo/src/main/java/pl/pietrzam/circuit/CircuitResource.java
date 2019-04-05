package pl.pietrzam.circuit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pietrzam.circuit.service.BadService;
import pl.pietrzam.circuit.service.ErrorService;
import pl.pietrzam.circuit.service.GoodService;
import pl.pietrzam.circuit.service.MaybeService;

@RestController
@RequestMapping(path = "/ping", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CircuitResource {

  private final GoodService good;
  private final BadService bad;
  private final MaybeService maybe;
  private final ErrorService error;

  @GetMapping("/good")
  @ResponseBody
  ResponseEntity pingGood() {
    return ResponseEntity.ok(good.ping());
  }

  @GetMapping("/bad")
  @ResponseBody
  ResponseEntity pingBad() {
    return ResponseEntity.ok(bad.ping());
  }

  @GetMapping("/maybe")
  @ResponseBody
  ResponseEntity pingMaybe() {
    return ResponseEntity.ok(maybe.ping());
  }

  @GetMapping("/error")
  @ResponseBody
  ResponseEntity pingError() {
    return ResponseEntity.ok(error.ping());
  }


}
