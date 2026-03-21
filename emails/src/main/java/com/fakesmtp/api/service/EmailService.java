package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.request.EmailRequestDto;
import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.EmailResponseDto;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing email operations.
 * Provides methods to retrieve email data.
 * @author Gilberto Vazquez
 */
public interface EmailService {

    EmailResponseDto saveEmail(EmailRequestDto emailRequestDto);

    /**
     * Retrieves all emails.
     * @return ListEmailResponse containing a list of EmailResponse objects.
     */
    PagesDataResponse<List<EmailResponseDto>> getAllEmails(Pageable pageable);

    /**
     * Retrieves a specific email by its ID.
     * @return EmailResponse object corresponding to the specified ID.
     */
    EmailResponseDto getEmailById(UUID idEmail);

    /**
     * Delete a specific email by its ID.
     * @param idEmail UUID of the email to delete.
     */
    void deleteEmail(UUID idEmail);

}
