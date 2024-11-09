package com.ic.authservice.service;

import com.ic.authservice.model.common.dto.response.CustomResponse;
import com.ic.authservice.request.LoginRequest;
import com.ic.authservice.response.TokenResponse;

/**
 * Service interface named {@link UserLoginService} for user login operations.
 * Provides methods for handling user login and generating authentication tokens.
 */
public interface UserLoginService {

    /**
     * Logs in a user by processing the provided login request and generating authentication tokens.
     *
     * @param loginRequest the login request containing user credentials (email and password)
     * @return a {@link CustomResponse} containing the {@link TokenResponse} with authentication tokens
     */
    CustomResponse<TokenResponse> login(final LoginRequest loginRequest);

}
