# Circuit breaker playground

This project is meant to create POC of app with circuit breaker using Java 11, SpringBoot 2 and Resilience4j.


### How to run

```
./gradlew clean build
docker-compose build
docker-compose up
```

### What will be running?
Docker will start:
* dummy Java with few endpoints
* demo app with exposed endpoints on port 8080
