package com.microservice.smtp.config.smtp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

/**
 * SMTP listener.
 * @author Gilberto Vazquez
 */
@Component
public class SMTPListener implements MessageHandlerFactory {


    public SMTPListener(EmailContentService emailContentService,
                        EmailRepository emailRepository,
                        MinioClientService minioClientService,
                        @Value("${minio.bucket-name}") String bucketName) {
        this.emailContentService = emailContentService;
        this.emailRepository = emailRepository;
        this.minioClientService = minioClientService;
        this.bucketName = bucketName;
    }

    /**
     * Create a new message handler per SMTP transaction.
     * @return message handler.
     */
    @Override
    public MessageHandler create(MessageContext ctx) {

        return new SmtpMessageHandler(emailContentService, emailRepository, minioClientService, bucketName);
    }

}
