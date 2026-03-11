package com.fakesmtp.api.dto.request;

import com.fakesmtp.api.dto.response.MediaResponse;
import com.fakesmtp.api.enums.EmailStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object representing an email response.
 * @author Gilberto Vazquez
 */
public record EmailRequestDto(
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
       String contentType
) {}
