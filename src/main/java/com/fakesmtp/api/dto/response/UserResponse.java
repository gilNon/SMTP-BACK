package com.fakesmtp.api.dto.response;

import java.time.Instant;

/**
 * Data Transfer Object representing a user response.
 * @author Gilberto Vazquez
 */
public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String role,
        Instant createdAt,
        Instant updatedAt
) {}
