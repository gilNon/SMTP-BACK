package com.fakesmtp.api.repository;

import com.fakesmtp.api.enums.ConfigurationTypes;
import com.fakesmtp.api.model.ConfigurationEntity;
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

    /**
     * Finds a configuration by type and value.
     * @param type the type of the configuration
     * @param value the value of the configuration
     * @return the configuration entity
     */
    Optional<ConfigurationEntity> findByTypeAndValue(ConfigurationTypes type, String value);

    /**
     * Checks if a configuration exists by type.
     * @param type the type of the configuration
     * @return true if the configuration exists, false otherwise
     */
    Boolean existsByType(ConfigurationTypes type);

}
