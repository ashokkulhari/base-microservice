package com.ic.productservice.client;

import com.ic.productservice.config.FeignClientConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface named {@link UserServiceClient} for interacting with the User Service.
 * Provides methods to validate tokens and retrieve authentication information.
 */
@FeignClient(name = "userservice", path = "/api/v1/users", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    /**
     * Validates the provided token by making a request to the user service.
     *
     * @param token the token to validate
     */
    @PostMapping("/validate-token")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "validateTokenFallback")
    void validateToken(@RequestParam String token);

    /**
     * Retrieves authentication information based on the provided token.
     *
     * @param token the token to use for retrieving authentication information
     * @return {@link UsernamePasswordAuthenticationToken} containing authentication details
     */
    @GetMapping("/authenticate")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "getAuthenticationFallback")
    UsernamePasswordAuthenticationToken getAuthentication(@RequestParam String token);


    default void validateTokenFallback(String token, Throwable throwable) {
        System.err.println("Fallback executed for validateToken: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }

    default UsernamePasswordAuthenticationToken getAuthenticationFallback(String token, Throwable throwable) {
        System.err.println("Fallback executed for getAuthentication: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }
}
