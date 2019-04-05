package pl.pietrzam.circuit;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HealthCheckConfigTest {

  @Autowired
  WebApplicationContext context;

  @LocalManagementPort
  int port;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  @DisplayName("Health Actuator endpoint should be open")
  void healthCheckEndpointOpen() {
    given()
        .get("/health")

        .then()
        .log().ifValidationFails()
        .statusCode(200);
  }

}