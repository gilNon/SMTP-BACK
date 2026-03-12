package com.microservice.smtp.config.smtp;

import com.microservice.smtp.restclient.EmailRestClient;
import com.microservice.smtp.restclient.MediaRestClient;
import com.microservice.smtp.service.EmailContentService;
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

    private final EmailRestClient emailRestClient;
    private final MediaRestClient mediaRestClient;
    private final EmailContentService emailContentService;

    public SMTPListener(EmailRestClient emailRestClient,
                        MediaRestClient mediaRestClient,
                        EmailContentService emailContentService) {
        this.emailRestClient = emailRestClient;
        this.mediaRestClient = mediaRestClient;
        this.emailContentService = emailContentService;
    }

    /**
     * Create a new message handler per SMTP transaction.
     * @return message handler.
     */
    @Override
    public MessageHandler create(MessageContext ctx) {

        return new SmtpMessageHandler(emailRestClient, mediaRestClient, emailContentService);
    }

}
