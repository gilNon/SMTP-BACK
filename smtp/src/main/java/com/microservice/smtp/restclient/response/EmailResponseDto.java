package com.microservice.smtp.restclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object representing an email response.
 * @author Gilberto Vazquez
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailResponseDto(
       UUID idEmail,
       String htmlContent,
       String textContent,
       String subject,
       String senderAddress,
       String senderName,
       String recipientAddress,
       String recipientName,
       String cc,
       String bcc,
       String contentType,
       Instant createdAt,
       Instant updatedAt
) {}
