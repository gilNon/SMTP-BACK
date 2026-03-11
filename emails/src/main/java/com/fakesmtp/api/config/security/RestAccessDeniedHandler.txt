package com.fakesmtp.api.config.security;

import com.fakesmtp.api.dto.response.ErrorResponse;
import com.fakesmtp.api.enums.MessageErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * Access denied handler.
 * @author Gilberto Vazquez
 */
@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    /**
     * Handle access denied exception.
     * @param request request.
     * @param response response.
     * @param accessDeniedException access denied exception.
     * @throws IOException io exception.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.name(),
                MessageErrors.ACCESS_DENIED.getMessage(),
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
