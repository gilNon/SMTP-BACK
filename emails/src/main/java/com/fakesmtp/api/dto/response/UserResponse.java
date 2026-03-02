package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object representing a user response.
 * @author Gilberto Vazquez
 */
public record UserResponse(
        UUID idUser,
        String firstName,
        String lastName,
        String email,
        String role,
        Boolean status,
        Instant createdAt,
        Instant updatedAt
) {}
