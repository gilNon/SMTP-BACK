package com.fakesmtp.api.dto.response;

import com.fakesmtp.api.enums.EmailStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class EmailResponseDto {
    private UUID idEmail;
    private String htmlContent;
    private String textContent;
    private String subject;
    private String senderAddress;
    private String senderName;
    private String recipientAddress;
    private String recipientName;
    private EmailStatus status;
    private List<MediaResponse> attachments;
    private String cc;
    private String bcc;
    private String contentType;
    private Instant createdAt;
    private Instant updatedAt;

}
