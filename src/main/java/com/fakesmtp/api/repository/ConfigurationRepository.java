package com.fakesmtp.api.repository;

import com.fakesmtp.api.enums.ConfigurationTypes;
import com.fakesmtp.api.model.ConfigurationEntity;
import com.fakesmtp.api.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Configuration repository.
 * @author Gilberto Vazquez
 */
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, UUID> {

    /**
     * Finds a configuration by type.
     * @param type the type of the configuration
     * @return the configuration entity
     */
    Optional<ConfigurationEntity> findByType(ConfigurationTypes type);

    Optional<ConfigurationEntity> findByTypeAndValue(ConfigurationTypes type, String value);

    Optional<ConfigurationEntity> findByApplicationAndType(ApplicationEntity application, ConfigurationTypes type);

}
