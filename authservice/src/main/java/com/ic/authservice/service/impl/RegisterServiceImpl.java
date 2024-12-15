package com.ic.authservice.service.impl;

import com.ic.authservice.client.UserServiceClient;
import com.ic.authservice.request.RegisterRequest;
import com.ic.authservice.service.RegisterService;
import com.ic.common.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link RegisterService} interface.
 * Handles the logic for user registration by forwarding the request to the {@link UserServiceClient}.
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserServiceClient userServiceClient;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest the registration request containing user details (email, password, etc.)
     * @return the registered {@link User} object
     */
    @Override
    public User registerUser(RegisterRequest registerRequest) {
        return userServiceClient.register(registerRequest).getBody();
    }

}
