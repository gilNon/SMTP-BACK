package com.fakesmtp.api.mapper;

import com.fakesmtp.api.dto.response.ConfigurationResponse;
import com.fakesmtp.api.model.ConfigurationEntity;

/**
 * Mapper class for converting ConfigurationEntity to ConfigurationResponse.
 * @author Gilberto Vazquez
 */
public class ConfigurationMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigurationMapper() {
        //private constructor to prevent instantiation
    }

    /**
     * Converts a ConfigurationEntity to a ConfigurationResponse.
     * @param configuration the ConfigurationEntity to convert
     * @return the ConfigurationResponse
     */
    public static ConfigurationResponse toConfigurationResponse(ConfigurationEntity configuration) {
        return new ConfigurationResponse(
                configuration.getIdConfiguration(),
                configuration.getType().name(),
                configuration.getValue(),
                configuration.getCreatedAt(),
                configuration.getUpdatedAt()
        );
    }
}
