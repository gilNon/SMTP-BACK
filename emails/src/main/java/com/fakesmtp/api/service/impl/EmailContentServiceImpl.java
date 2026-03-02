package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.config.smtp.ReceivedEmail;
import com.fakesmtp.api.service.EmailContentService;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * Email content service implementation.
 * @author Gilberto Vazquez
 */
@Service
public class EmailContentServiceImpl implements EmailContentService {

    /**
     * Converts the input stream to a MimeMessage.
     * @param data input stream.
     * @return MimeMessage.
     * @throws MessagingException if the conversion fails.
     */
    @Override
    public MimeMessage convertToMimeMessage(InputStream data) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        try {
            return new MimeMessage(session, data);
        } catch (MessagingException e) {
            throw new MessagingException();
        }
    }

    /**
     * Extracts the received email from the input stream.
     * @param data input stream.
     * @return ReceivedEmail.
     * @throws Exception if the extraction fails.
     */
    @Override
    public ReceivedEmail extractReceivedEmail(InputStream data) throws Exception {
        ReceivedEmail receivedEmail = new ReceivedEmail();
        MimeMessage message;
        try {
            message = this.convertToMimeMessage(data);
            receivedEmail.setSubject(message.getSubject());
            receivedEmail.setSenderAddress(InternetAddress.toString(message.getFrom()));
            InternetAddress[] recipientAddresses = InternetAddress.parse(InternetAddress.toString(message.getAllRecipients()));
            receivedEmail.setRecipientAddress(InternetAddress.toString(recipientAddresses));
            receivedEmail.setRecipientName(recipientAddresses[0].getPersonal());
            receivedEmail.setContentType(message.getContentType());
            // Use here Apache library for parsing
            MimeMessageParser messageParser = new MimeMessageParser(message);
            messageParser.parse(); // very important to parse before getting data
            receivedEmail.setCc(messageParser.getCc().toString());
            receivedEmail.setBcc(messageParser.getBcc().toString());
            receivedEmail.setAttachments(messageParser.getAttachmentList());
            receivedEmail.setHtmlContent(messageParser.getHtmlContent());
            receivedEmail.setTextContent(messageParser.getPlainContent());
            return receivedEmail;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
