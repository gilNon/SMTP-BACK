package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.request.EmailRequestDto;
import com.fakesmtp.api.dto.response.EmailResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling emails API requests.
 * Provides endpoints to fetch information about emails.
 * @author Gilberto Vazquez
 */
@RestController
@RequestMapping("/api/v1/emails")
@AllArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    ResponseEntity<EmailResponse> saveEmail(@RequestBody EmailRequestDto emailRequestDto) {
        return new ResponseEntity<>(emailService.saveEmail(emailRequestDto), HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all emails.
     * @return ResponseEntity containing a list of EmailResponse objects.
     */
    @GetMapping
    public ResponseEntity<PagesDataResponse<List<EmailResponse>>> getMails(
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok(emailService.getAllEmails(pageable));
    }

    /**
     * Endpoint to retrieve a specific email by its ID.
     * @param idMail UUID of the email to retrieve.
     * @return ResponseEntity containing the EmailResponse object.
     */
    @GetMapping("/{idMail}")
    public ResponseEntity<EmailResponse> getMailById(
            @PathVariable UUID idMail
    ) {
        log.info("Fetching mail with ID: {}", idMail);
        return ResponseEntity.ok(emailService.getEmailById(idMail));
    }


    /**
     * Endpoint to delete an email by its ID.
     * @param idEmail UUID of the email to delete.
     */
    @DeleteMapping("/{idEmail}")
    public ResponseEntity<Void> deleteEmailById(
            @PathVariable UUID idEmail
    ) {
        emailService.deleteEmail(idEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
