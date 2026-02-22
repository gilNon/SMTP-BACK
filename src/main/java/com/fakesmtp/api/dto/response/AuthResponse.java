package com.fakesmtp.api.dto.response;

/**
 * Data Transfer Object representing an authentication response.
 * @author Gilberto Vazquez
 */
public record AuthResponse(
        String token,
        String email
){}
