package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.enums.EmailStatus;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.EmailMapper;
import com.fakesmtp.api.model.EmailEntity;
import com.fakesmtp.api.repository.EmailRepository;
import com.fakesmtp.api.repository.UserRepository;
import com.fakesmtp.api.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 Implementation of the EmailService interface to handle email-related operations.
 @author Gilberto Vazquez
*/
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final MinioClientService minioClientService;
    private final String bucketName;

    public EmailServiceImpl(EmailRepository emailRepository,
                            UserRepository userRepository,
                            MinioClientService minioClientService,
                            @Value("${minio.bucket-name}")
                            String bucketName) {
        this.emailRepository = emailRepository;
        this.minioClientService = minioClientService;
        this.bucketName = bucketName;
    }

    /**
     * Retrieves all emails from the repository and maps them to EmailResponse DTOs.
     * @return ListEmailResponse containing a list of EmailResponse objects.
     */
    @Override
    public PagesDataResponse<List<EmailResponse>> getAllEmails(Pageable pageable) {

        Page<EmailEntity> emailEntityPage = emailRepository.findAll(pageable);
        emailEntityPage.forEach(this::enrichAttachmentsWithPresignedUrl);
        return EmailMapper.toListEmailResponse(emailRepository.findAll(pageable));
    }

    private void enrichAttachmentsWithPresignedUrl(EmailEntity emailEntity) {
        emailEntity.getAttachments()
                .forEach(mediaEntity -> mediaEntity.setMediaURL(minioClientService.getFilePreSigned(bucketName,mediaEntity.getFolder() + "/" + mediaEntity.getFileName() )));
    }

    /**
     * Retrieves a specific email by its ID and maps it to an EmailResponse DTO.
     * @param idEmail UUID of the email to retrieve.
     * @return EmailResponse object corresponding to the specified ID.
     * @throws RuntimeException if the email is not found.
     */
    @Override
    public EmailResponse getEmailById(UUID idEmail) {


        EmailEntity emailEntity = emailRepository.findById(idEmail).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND, MessageErrors.EMAIL_NOT_FOUND.getMessage())
        );
       enrichAttachmentsWithPresignedUrl(emailEntity);

        return EmailMapper.toEmailResponse(emailEntity);
    }

    /**
     * Delete a specific email by its ID.
     *
     * @param idEmail        UUID of the email to delete.
     */
    @Override
    public void deleteEmail(UUID idEmail) {
        EmailEntity emailEntity = emailRepository.findById(idEmail).
                orElseThrow(() -> new GeneralException(HttpStatus.NOT_FOUND, MessageErrors.EMAIL_NOT_FOUND.getMessage()));

        emailEntity.setStatus(EmailStatus.DELETED);

        emailRepository.save(emailEntity);
    }

}
