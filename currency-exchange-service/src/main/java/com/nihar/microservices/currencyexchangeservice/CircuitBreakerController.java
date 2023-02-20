package com.nihar.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name="default", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name="default") // 10sec only allow 2 api calls
    @Bulkhead(name="default") // Configure maximum concurrent api calls
    public String SampleAPI() {
        logger.info("Sample API Method");
        ResponseEntity<String> result = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
        return "Sample API Result" + result.getBody();
    }

    public String hardcodedResponse(Exception exception) {
        logger.info("Fallback response Method");
        return "fallback-response";
    }
}
