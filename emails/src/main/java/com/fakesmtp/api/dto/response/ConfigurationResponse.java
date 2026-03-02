package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Configuration response.
 * @author Gilberto Vazquez
 */
public record ConfigurationResponse(
        UUID idConfiguration,
        String type,
        String value,
        Instant createdAt,
        Instant updatedAt
) {
}
