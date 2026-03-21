package com.fakesmtp.api.restclient.request;

public record EmailRequestDto(
       String htmlContent,
       String textContent,
       String subject,
       String senderAddress,
       String senderName,
       String recipientAddress,
       String recipientName,
       String cc,
       String bcc,
       String contentType
) {}
