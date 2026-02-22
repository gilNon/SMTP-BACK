package com.fakesmtp.api.config.smtp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Received email.
 * @author Gilberto Vazquez
 */
@Getter
@Setter
@ToString
public class ReceivedEmail {
    private String subject;
    private String htmlContent;
    private String textContent;
    private String senderAddress;
    private String senderName;
    private String recipientAddress;
    private String recipientName;
    private String cc;
    private String bcc;
    private String contentType;
    private List<DataSource> attachments = new ArrayList<>();

}
