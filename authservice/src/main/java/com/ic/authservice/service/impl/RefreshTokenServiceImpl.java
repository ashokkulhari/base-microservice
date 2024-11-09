package com.ic.authservice.service.impl;

import com.ic.authservice.client.UserServiceClient;
import com.ic.authservice.model.common.dto.response.CustomResponse;
import com.ic.authservice.request.TokenRefreshRequest;
import com.ic.authservice.response.TokenResponse;
import com.ic.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link RefreshTokenService} interface.
 * Handles the logic for refreshing access tokens using a refresh token via the {@link UserServiceClient}.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserServiceClient userServiceClient;

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param tokenRefreshRequest the request containing the refresh token
     * @return a {@link CustomResponse} containing the {@link TokenResponse} with the new access and refresh tokens
     */
    @Override
    public CustomResponse<TokenResponse> refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        return userServiceClient.refreshToken(tokenRefreshRequest);
    }

}
