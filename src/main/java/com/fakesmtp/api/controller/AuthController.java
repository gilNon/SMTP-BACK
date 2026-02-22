package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.request.LoginRequest;
import com.fakesmtp.api.dto.request.RegisterRequest;
import com.fakesmtp.api.dto.response.AuthResponse;
import com.fakesmtp.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication endpoints.
 *
 * @author Gilberto Vazquez
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authServiceImpl;

    /** 
     * Register a new user.
     *
     * @param request the registration request containing user details
     * @return the authentication response containing the token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registering new user with email: {}", request.email());
        AuthResponse response = authServiceImpl.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login a user.
     *
     * @param request the login request containing user credentials
     * @return the authentication response containing the token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("User login attempt with email: {}", request.email());
        AuthResponse response = authServiceImpl.login(request);
        return ResponseEntity.ok(response);
    }

}
