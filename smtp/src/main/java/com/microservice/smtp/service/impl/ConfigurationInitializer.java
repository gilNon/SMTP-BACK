package com.microservice.smtp.service.impl;

import com.microservice.smtp.config.smtp.ApiKeyGenerator;
import com.microservice.smtp.model.ConfigurationEntity;
import com.microservice.smtp.model.ConfigurationTypes;
import com.microservice.smtp.properties.SmtpProperties;
import com.microservice.smtp.repository.ConfigurationRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigurationInitializer implements CommandLineRunner {
    
    private final ConfigurationRepository configurationRepository;
    private final SmtpProperties smtpProperties;

    @Override
    @Transactional
    public void run(@NotNull String... args) throws Exception {

        for (ConfigurationTypes type: ConfigurationTypes.values()) {
            if (!configurationRepository.existsByType(type)) {
                ConfigurationEntity configuration = new ConfigurationEntity();
                configuration.setType(type);
                configuration.setValue(getDefaultValue(type));
                log.info("CREATING DEFAULT {}...", type.name());
                configurationRepository.save(configuration);
            }
        }

    }

    private String getDefaultValue(ConfigurationTypes type) {
        return switch (type) {
            case PORT_SMTP -> String.valueOf(smtpProperties.getPort());
            case HOST_SMTP -> smtpProperties.getHost();
            case USER_SMTP -> smtpProperties.getUser();
            case PASSWORD_SMTP -> ApiKeyGenerator.generateApiKey();
            case SSL_SMTP -> smtpProperties.getSsl();
            case TLS_SMTP -> smtpProperties.getTls();
            case APP_FIRST_INITIALIZER ->  "true";
        };
    }


}
