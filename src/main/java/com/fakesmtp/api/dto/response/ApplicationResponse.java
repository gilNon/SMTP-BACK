package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Application response dto.
 * @author Gilberto Vazquez
 */
public record ApplicationResponse(
        UUID idApplication,
        Boolean active,
        List<UserResponse> users,
        List<ConfigurationResponse> configurations,
        Instant createdAt,
        Instant updatedAt
) {
}
