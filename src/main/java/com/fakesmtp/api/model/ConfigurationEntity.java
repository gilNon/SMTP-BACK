package com.fakesmtp.api.model;

import com.fakesmtp.api.enums.ConfigurationTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * Configuration entity.
 * @author Gilberto Vazquez
 */
@Entity
@Table(name = "configurations")
@Getter
@Setter
public class ConfigurationEntity extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_configuration")
    private UUID idConfiguration;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    private ConfigurationTypes type;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_application")
    private ApplicationEntity application;

}
