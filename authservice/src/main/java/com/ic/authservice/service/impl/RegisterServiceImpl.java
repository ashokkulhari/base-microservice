package com.ic.authservice.service.impl;

import com.ic.authservice.client.UserServiceClient;
import com.ic.authservice.model.User;
import com.ic.authservice.request.RegisterRequest;
import com.ic.authservice.service.RegisterService;
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
        System.out.println("---service ---registerUser ---");
        return userServiceClient.register(registerRequest).getBody();
    }

}
