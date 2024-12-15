package com.ic.apigateway.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface named {@link UserServiceClient} for interacting with the User Service.
 * This client is used to perform various operations related to user management,
 * such as validating tokens.
 */
@FeignClient(name = "userservice", path = "/api/v1/users")
public interface UserServiceClient {


    @PostMapping("/validate-token")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "validateTokenFallback")
    void validateToken(@RequestParam String token);

    default void validateTokenFallback(String token, Throwable throwable) {
        System.err.println("Fallback executed for validateToken: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }

}
