package com.fakesmtp.api.mapper;

import com.fakesmtp.api.config.smtp.ReceivedEmail;
import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.PaginationResponse;
import com.fakesmtp.api.enums.EmailStatus;
import com.fakesmtp.api.model.EmailEntity;
import com.fakesmtp.api.model.MediaEntity;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Mapper class for converting EmailEntity to EmailResponse.
 * @author Gilberto Vazquez
 */
public class EmailMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private EmailMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts an EmailEntity to an EmailResponse.
     *
     * @param emailEntity the EmailEntity to convert
     * @return the corresponding EmailResponse
     */
    public static EmailResponse toEmailResponse(EmailEntity emailEntity) {

        return new EmailResponse(
                emailEntity.getIdEmail(),
                emailEntity.getHtmlContent(),
                emailEntity.getTextContent(),
                emailEntity.getSubject(),
                emailEntity.getSenderAddress(),
                emailEntity.getSenderName(),
                emailEntity.getRecipientAddress(),
                emailEntity.getRecipientName(),
                emailEntity.getStatus(),
                emailEntity.getAttachments()
                        .stream()
                        .map(MediaMapper::toMediaResponse)
                        .toList(),
                emailEntity.getCc(),
                emailEntity.getBcc(),
                emailEntity.getContentType(),
                emailEntity.getCreatedAt(),
                emailEntity.getUpdatedAt()
        );
    }

    /**
     * Converts a Page of EmailEntity to a ListEmailResponse containing a list of EmailResponse.
     *
     * @param page the Page of EmailEntity to convert
     * @return the corresponding ListEmailResponse with pagination details
     */
    public static PagesDataResponse<List<EmailResponse>> toListEmailResponse(Page<EmailEntity> page) {

        List<EmailResponse> emailResponses = page.getContent()
                .stream()
                .map(EmailMapper::toEmailResponse)
                .toList();

        PaginationResponse paginationResponse = new PaginationResponse(page);

        return new PagesDataResponse<>(emailResponses, Instant.now(), paginationResponse);

    }

    /**
     * Convert ReceivedEmail to EmailEntity.
     * @param email email received to save.
     * @return email entity.
     */
    public static EmailEntity toEmailEntity(ReceivedEmail email) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setCc(email.getCc());
        emailEntity.setBcc(email.getBcc());
        emailEntity.setStatus(EmailStatus.RECEIVED);
        emailEntity.setContentType(email.getContentType());
        emailEntity.setRecipientAddress(email.getRecipientAddress());
        emailEntity.setRecipientName(email.getRecipientName());
        emailEntity.setSenderAddress(email.getSenderAddress());
        emailEntity.setSenderName(email.getSenderName());
        emailEntity.setSubject(email.getSubject());
        emailEntity.setCreatedAt(Instant.now());
        emailEntity.setUpdatedAt(Instant.now());
        emailEntity.setHtmlContent(email.getHtmlContent());
        emailEntity.setTextContent(email.getTextContent());
        emailEntity.setAttachments(email.getAttachments().stream().map(
                attachment -> {
                    MediaEntity mediaEntity = new MediaEntity();
                    mediaEntity.setFileName(attachment.getName());
                    mediaEntity.setCreatedAt(Instant.now());
                    mediaEntity.setUpdatedAt(Instant.now());
                    mediaEntity.setDataSource(attachment);
                    mediaEntity.setFolder(UUID.randomUUID().toString());
                    mediaEntity.setEmail(emailEntity);

                    return mediaEntity;
                }
        ).toList());
        return emailEntity;
    }

}
