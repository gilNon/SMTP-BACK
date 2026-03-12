package com.microservice.smtp.config.smtp;

import com.microservice.smtp.mapper.MultipartMapper;
import com.microservice.smtp.restclient.EmailRestClient;
import com.microservice.smtp.restclient.MediaRestClient;
import com.microservice.smtp.restclient.request.EmailRequestDto;
import com.microservice.smtp.restclient.response.EmailResponseDto;
import com.microservice.smtp.service.EmailContentService;
import lombok.extern.slf4j.Slf4j;
import org.subethamail.smtp.MessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * SMTP message handler.
 * @author Gilberto Vazquez
 */
@Slf4j
public final class SmtpMessageHandler implements MessageHandler {

    private final EmailRestClient emailRestClient;
    private final MediaRestClient mediaRestClient;
    private final EmailContentService emailContentService;

    private final List<String> recipients = new ArrayList<>();
    private String from;

    /**
     * Constructor.
     */
    public SmtpMessageHandler(EmailRestClient emailRestClient,
                              MediaRestClient mediaRestClient,
                              EmailContentService emailContentService) {
        this.emailRestClient = emailRestClient;
        this.mediaRestClient = mediaRestClient;
        this.emailContentService = emailContentService;
    }

    @Override
    public void from(String from) {
        this.from = from;
    }

    @Override
    public void recipient(String recipient) {
        this.recipients.add(recipient);
    }

    /**
     * Handles the data of the email.
     * @param data the data of the email
     * @return null
     * @throws IOException if an I/O error occurs
     */
    @Override
    public String data(InputStream data) throws IOException {
        try {
            ReceivedEmail receivedEmail = emailContentService.extractReceivedEmail(data);
            log.info("Received email from={} to={} subject={}", from, recipients, receivedEmail.getSubject());

            EmailRequestDto emailRequestDto = new EmailRequestDto(
                    receivedEmail.getHtmlContent(),
                    receivedEmail.getTextContent(),
                    receivedEmail.getSubject(),
                    receivedEmail.getSenderAddress(),
                    receivedEmail.getSenderName(),
                    receivedEmail.getRecipientAddress(),
                    receivedEmail.getRecipientName(),
                    receivedEmail.getCc(),
                    receivedEmail.getBcc(),
                    receivedEmail.getContentType()
            );

            EmailResponseDto emailResponseDto = emailRestClient.saveEmail(emailRequestDto);
            mediaRestClient.saveMedia(MultipartMapper.convert(receivedEmail.getAttachments()), emailResponseDto.idEmail().toString());

            return null;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    @Override
    public void done() {
        // no-op
    }
}
