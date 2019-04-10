package pl.pietrzam.circuit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pietrzam.circuit.service.PingService;
import pl.pietrzam.circuit.service.ServiceFactory;
import pl.pietrzam.circuit.service.ServiceType;

@RestController
@RequestMapping(path = "/", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DynamicCircuitResource {
  
  private final ServiceFactory serviceFactory;

  @GetMapping("/i/want/{pingType}")
  @ResponseBody
  ResponseEntity pingByDynamicType(@PathVariable final String pingType) {
    var serviceType = ServiceType.valueOfIgnoringCase(pingType);
    var pingService = serviceFactory.serviceFor(serviceType);
    return ResponseEntity.ok(pingService.ping());
  }

}
