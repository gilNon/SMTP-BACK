package com.fakesmtp.api.restclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record MediaResponseClientDto(
        UUID idMedia,
        UUID idEmail,
        String fileName,
        String folder,
        String url,
        Instant createdAt,
        Instant updatedAt
) {
}
