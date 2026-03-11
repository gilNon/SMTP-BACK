package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.request.EmailRequestDto;
import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.enums.EmailStatus;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.EmailMapper;
import com.fakesmtp.api.model.EmailEntity;
import com.fakesmtp.api.repository.EmailRepository;
import com.fakesmtp.api.service.EmailService;
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

    public EmailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public EmailResponse saveEmail(EmailRequestDto emailRequestDto) {
        EmailEntity emailEntity = emailRepository.save(EmailMapper.toEmailEntity(emailRequestDto));
        return EmailMapper.toEmailResponse(emailEntity);
    }

    /**
     * Retrieves all emails from the repository and maps them to EmailResponse DTOs.
     * @return ListEmailResponse containing a list of EmailResponse objects.
     */
    @Override
    public PagesDataResponse<List<EmailResponse>> getAllEmails(Pageable pageable) {

        Page<EmailEntity> emailEntityPage = emailRepository.findAll(pageable);

        return EmailMapper.toListEmailResponse(emailRepository.findAll(pageable));
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
