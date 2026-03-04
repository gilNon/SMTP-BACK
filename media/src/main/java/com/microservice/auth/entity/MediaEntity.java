package com.microservice.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a media record associated with an email in the database.
 * @author Gilberto Vazquez
 */
@Entity
@Table(name = "media")
@Getter
@Setter
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_media")
    private UUID idMedia;

    @Column(name = "id_email")
    private UUID idEmail;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "folder")
    private String folder;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
