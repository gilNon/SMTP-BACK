package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.enums.EmailStatus;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.EmailMapper;
import com.fakesmtp.api.model.EmailEntity;
import com.fakesmtp.api.model.UserEntity;
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
    private final UserRepository userRepository;
    private final MinioClientService minioClientService;
    private final String bucketName;

    public EmailServiceImpl(EmailRepository emailRepository,
                            UserRepository userRepository,
                            MinioClientService minioClientService,
                            @Value("${minio.bucket-name}")
                            String bucketName) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.minioClientService = minioClientService;
        this.bucketName = bucketName;
    }

    /**
     * Retrieves all emails from the repository and maps them to EmailResponse DTOs.
     * @return ListEmailResponse containing a list of EmailResponse objects.
     */
    @Override
    public PagesDataResponse<List<EmailResponse>> getAllEmails(Pageable pageable, String emailUser) {

        UserEntity user = userRepository.findByEmail(emailUser).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage())
        );

        Page<EmailEntity> page = emailRepository.findAllByApplication(user.getApplication(), pageable);
        page.getContent().forEach(email ->
                email.getAttachments().forEach(media ->
                        media.setMediaURL(minioClientService.getFilePreSigned(
                                bucketName,
                                media.getFolder() + "/" + media.getFileName()))));

        return EmailMapper.toListEmailResponse(page);
    }

    /**
     * Retrieves a specific email by its ID and maps it to an EmailResponse DTO.
     * @param idEmail UUID of the email to retrieve.
     * @return EmailResponse object corresponding to the specified ID.
     * @throws RuntimeException if the email is not found.
     */
    @Override
    public EmailResponse getEmailById(UUID idEmail, String emailUser) {

        UserEntity user = userRepository.findByEmail(emailUser).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage())
        );

        EmailEntity email = emailRepository.findByIdEmailAndApplication(
                idEmail,
                user.getApplication()).orElseThrow(() ->
                 new GeneralException(HttpStatus.NOT_FOUND,
                         MessageErrors.EMAIL_NOT_FOUND.getMessage())
        );

        email.getAttachments().forEach(media ->
                media.setMediaURL(minioClientService.getFilePreSigned(
                        bucketName,
                        media.getFolder() + "/" + media.getFileName())
                ));

        return EmailMapper.toEmailResponse(email);
    }

    /**
     * Delete a specific email by its ID.
     *
     * @param idEmail        UUID of the email to delete.
     * @param emailUser Email of the user who is deleting the email.
     */
    @Override
    public void deleteEmail(UUID idEmail, String emailUser) {
        UserEntity user = userRepository.findByEmail(emailUser).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage())
        );

        EmailEntity email = emailRepository.findByIdEmailAndApplication(idEmail, user.getApplication()).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.EMAIL_NOT_FOUND.getMessage())
        );

        email.setStatus(EmailStatus.DELETED);
        emailRepository.save(email);
    }

}
