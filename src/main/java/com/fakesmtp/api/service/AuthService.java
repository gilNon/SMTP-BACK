package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.request.LoginRequest;
import com.fakesmtp.api.dto.request.RegisterRequest;
import com.fakesmtp.api.dto.response.AuthResponse;

/**
 * Service interface for authentication.
 * @author Gilberto Vazquez
 */
public interface AuthService {

    /**
     * Register a new user.
     * @param request the registration request
     * @return the authentication response
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Login a user.
     * @param request the login request
     * @return the authentication response
     */
    AuthResponse login(LoginRequest request);

}
