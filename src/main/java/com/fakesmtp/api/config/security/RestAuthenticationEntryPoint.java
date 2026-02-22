package com.fakesmtp.api.config.security;

import com.fakesmtp.api.dto.response.ErrorResponse;
import com.fakesmtp.api.enums.MessageErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Authentication entry point.
 * @author Gilberto Vazquez
 */
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * Commence.
     * @param request request.
     * @param response response.
     * @param authException Exception.
     * @throws IOException Exception.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        String message = MessageErrors.INVALID_CREDENTIALS.getMessage();
        Throwable cause = authException.getCause();
        if (authException instanceof UsernameNotFoundException || cause instanceof UsernameNotFoundException) {
            message = MessageErrors.USER_NOT_FOUND.getMessage();
        }

        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                request.getRequestURI(),
                Instant.now(),
                List.of()
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }

}
