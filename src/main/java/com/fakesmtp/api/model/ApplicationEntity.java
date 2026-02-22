package com.fakesmtp.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Application entity.
 * @author Gilberto Vazquez
 */
@Entity
@Table(name = "applications")
@Getter
@Setter
public class ApplicationEntity extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_application")
    private UUID idApplication;

    @OneToMany(
            mappedBy = "application",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<EmailEntity> emails;

    @OneToMany(
            mappedBy = "application",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ConfigurationEntity> configurations;

    @OneToMany(
            mappedBy = "application",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<UserEntity> users;

    @Column(name = "active")
    private Boolean active;

}
