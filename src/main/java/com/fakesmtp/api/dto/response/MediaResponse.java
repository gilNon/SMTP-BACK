package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object representing a media response.
 * @author Gilberto Vazquez
 */
public record MediaResponse(
        UUID idMedia,
        String mediaURL,
        String fileName,
        Instant createdAt,
        Instant updatedAt
) { }
