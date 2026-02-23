package com.fakesmtp.api.model;

import com.fakesmtp.api.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

/**
 * Entity representing an email record in the database.
 * @author Gilberto Vazquez
 */
@Entity
@Table(name = "emails")
@Getter
@Setter
@SQLRestriction("status <> 'DELETED'") // SQL restriction to exclude deleted emails
public class EmailEntity extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_email")
    private UUID idEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EmailStatus status;

    @OneToMany(
        mappedBy = "email",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    List<MediaEntity> attachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_application")
    private ApplicationEntity application;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "html_content")
    private String htmlContent;

    @Column(name = "text_content")
    private String textContent;

}
