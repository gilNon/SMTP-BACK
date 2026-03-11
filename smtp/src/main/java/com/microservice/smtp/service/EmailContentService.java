package com.microservice.smtp.service;

import com.microservice.smtp.config.smtp.ReceivedEmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

/**
 * Email content service.
 * @author Gilberto Vazquez
 */
public interface EmailContentService {
    /**
     * Convert to mime message.
     * @param data data request.
     * @return MimeMessage.
     * @throws MessagingException Exception.
     */
    MimeMessage convertToMimeMessage(InputStream data) throws MessagingException;

    /**
     * Extract received email.
     * @param data data request.
     * @return ReceivedEmail.
     * @throws Exception Exception.
     */
    ReceivedEmail extractReceivedEmail(InputStream data) throws Exception;
}
