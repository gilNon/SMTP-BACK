package com.fakesmtp.api.dto.response;

import com.fakesmtp.api.enums.EmailStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object representing an email response.
 * @author Gilberto Vazquez
 */
public record EmailResponse(
       UUID idEmail,
       String htmlContent,
       String textContent,
       String subject,
       String senderAddress,
       String senderName,
       String recipientAddress,
       String recipientName,
       EmailStatus status,
       List<MediaResponse> attachments,
       String cc,
       String bcc,
       String contentType,
       Instant createdAt,
       Instant updatedAt
) {}
