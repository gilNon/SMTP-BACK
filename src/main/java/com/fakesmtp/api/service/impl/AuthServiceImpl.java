package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.request.LoginRequest;
import com.fakesmtp.api.dto.request.RegisterRequest;
import com.fakesmtp.api.dto.response.AuthResponse;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.UserMapper;
import com.fakesmtp.api.model.UserEntity;
import com.fakesmtp.api.repository.ConfigurationRepository;
import com.fakesmtp.api.repository.UserRepository;
import com.fakesmtp.api.config.security.JwtService;
import com.fakesmtp.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for authentication.
 * @author Gilberto Vazquez
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfigurationRepository configurationRepository;

    /**
     * Registers a new user.
     * @param request the registration request
     * @return the authentication response
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new GeneralException(
                    HttpStatus.BAD_REQUEST,
                    MessageErrors.EMAIL_ALREADY_EXISTS.getMessage()
            );
        }


        UserEntity user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getEmail());
    }

    /**
     * Logs in a user.
     * @param request the login request
     * @return the authentication response
     */
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new GeneralException(
                        HttpStatus.BAD_REQUEST,
                        MessageErrors.USER_NOT_FOUND.getMessage())
                );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getEmail());
    }

}
