package com.microservice.auth.controller.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MediaResponseDto(
        UUID idMedia,
        UUID idEmail,
        String fileName,
        String folder,
        String url,
        Instant createdAt,
        Instant updatedAt
) {
}
