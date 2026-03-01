package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.List;

/**
 * Application response dto.
 * @author Gilberto Vazquez
 */
public record ServerResponse(
        List<ConfigurationResponse> configurations,
        Instant createdAt,
        Instant updatedAt
) {
}
