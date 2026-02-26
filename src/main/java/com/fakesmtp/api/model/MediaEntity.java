package com.fakesmtp.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.activation.DataSource;
import java.util.UUID;

/**
 * Entity representing a media record associated with an email in the database.
 * @author Gilberto Vazquez
 */
@Entity
@Table(name = "media")
@Getter
@Setter
public class MediaEntity extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_media")
    private UUID idMedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_email")
    private EmailEntity email;

    @Column(name = "file_name")
    private String fileName;

    @Transient
    private DataSource dataSource;

    @Column(name = "folder")
    private String folder;

    @Transient
    private String mediaURL;

}
