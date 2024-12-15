package com.ic.authservice.client;

import com.ic.authservice.model.common.dto.response.CustomResponse;
import com.ic.authservice.request.LoginRequest;
import com.ic.authservice.request.RegisterRequest;
import com.ic.authservice.request.TokenInvalidateRequest;
import com.ic.authservice.request.TokenRefreshRequest;
import com.ic.authservice.response.TokenResponse;
import com.ic.common.models.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface named {@link UserServiceClient} for interacting with the User Service.
 * This client is used to perform various operations related to user management,
 * such as registration, token validation, login, token refresh, and logout.
 */
@FeignClient(name = "userservice", path = "/api/v1/users")
public interface UserServiceClient {

    /**
     * Registers a new user with the provided registration request.
     *
     * @param request the registration request containing user details
     * @return the registered user
     */
    @PostMapping("/register")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "registerFallback")
    ResponseEntity<User> register(@RequestBody @Valid final RegisterRequest request);

    /**
     * Validates the given token by making a POST request to the User Service.
     *
     * @param token the token to be validated
     */
    @PostMapping("/validate-token")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "validateTokenFallback")
    void validateToken(@RequestParam String token);

    /**
     * Logs in a user with the provided login request.
     *
     * @param loginRequest the login request containing user credentials
     * @return the token response containing access and refresh tokens
     */
    @PostMapping("/login")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "loginUserFallback")
    CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest);

    /**
     * Refreshes the access token using the provided token refresh request.
     *
     * @param tokenRefreshRequest the token refresh request containing the refresh token
     * @return the token response containing new access and refresh tokens
     */
    @PostMapping("/refresh-token")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "refreshTokenFallback")
    CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest);

    /**
     * Logs out a user by invalidating the provided token.
     *
     * @param tokenInvalidateRequest the token invalidate request containing the token to be invalidated
     * @return a response indicating the result of the logout operation
     */
    @PostMapping("/logout")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "logoutFallback")
    CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest);


    default void registerFallback(RegisterRequest request, Throwable throwable) {
        System.err.println("Fallback executed for register: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }

    default void validateTokenFallback(String token, Throwable throwable) {
        System.err.println("Fallback executed for validateToken: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }
    default void loginUserFallback(LoginRequest loginRequest, Throwable throwable) {
        System.err.println("Fallback executed for loginUser: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }
    default void refreshTokenFallback(TokenRefreshRequest tokenRefreshRequest, Throwable throwable) {
        System.err.println("Fallback executed for refreshToken: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }
    default void logoutFallback(TokenInvalidateRequest tokenInvalidateRequest, Throwable throwable) {
        System.err.println("Fallback executed for logout: " + throwable.getMessage());
        throw new RuntimeException("User Service is unavailable. Please try again later.");
    }
}
