package com.fakesmtp.api.config.smtp;

import com.fakesmtp.api.mapper.EmailMapper;
import com.fakesmtp.api.model.ApplicationEntity;
import com.fakesmtp.api.model.EmailEntity;
import com.fakesmtp.api.model.MediaEntity;
import com.fakesmtp.api.repository.EmailRepository;
import com.fakesmtp.api.service.EmailContentService;
import com.fakesmtp.api.service.impl.MinioClientService;
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

    private final EmailContentService emailContentService;
    private final EmailRepository emailRepository;
    private final MinioClientService minioClientService;
    private final ApplicationEntity application;

    private final List<String> recipients = new ArrayList<>();
    private String from;
    private final String bucketName;

    /**
     * Constructor.
     * @param emailContentService the email content service
     * @param emailRepository the email repository
     * @param minioClientService the minio client service
     */
    public SmtpMessageHandler(EmailContentService emailContentService,
                              EmailRepository emailRepository,
                              MinioClientService minioClientService,
                              ApplicationEntity application,
                              String bucketName) {
        this.emailContentService = emailContentService;
        this.emailRepository = emailRepository;
        this.minioClientService = minioClientService;
        this.application = application;
        this.bucketName = bucketName;
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

            EmailEntity emailToSave = EmailMapper.toEmailEntity(receivedEmail);
            emailToSave.setApplication(application);

            if (!emailToSave.getAttachments().isEmpty()) {
                emailToSave.getAttachments().forEach(this::uploadMediaEntity);
            }

            emailRepository.save(emailToSave);
            return null;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Uploads a media entity to MinIO.
     * @param mediaEntity the media entity to upload
     */
    private void uploadMediaEntity(MediaEntity mediaEntity) {
        try {
            minioClientService.putObject(mediaEntity.getDataSource().getContentType(),
                    mediaEntity.getDataSource().getInputStream(),
                    mediaEntity.getDataSource().getInputStream().available(),
                    bucketName,
                    "/" + mediaEntity.getFolder() + "/" + mediaEntity.getDataSource().getName());
        } catch (IOException ioe) {
            log.error("Error uploading file to MinIO: {}", ioe.getMessage());
        }
    }

    @Override
    public void done() {
        // no-op
    }
}
