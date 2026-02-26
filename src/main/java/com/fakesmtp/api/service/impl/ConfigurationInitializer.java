package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.config.properties.SmtpProperties;
import com.fakesmtp.api.config.properties.UserDefaultProperties;
import com.fakesmtp.api.config.security.ApiKeyGenerator;
import com.fakesmtp.api.enums.ConfigurationTypes;
import com.fakesmtp.api.enums.Roles;
import com.fakesmtp.api.model.ConfigurationEntity;
import com.fakesmtp.api.model.UserEntity;
import com.fakesmtp.api.repository.ConfigurationRepository;
import com.fakesmtp.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigurationInitializer implements CommandLineRunner {
    
    private final ConfigurationRepository configurationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmtpProperties smtpProperties;
    private final UserDefaultProperties userDefaultProperties;
    
    /**
     * @param args
     * @throws Exception
     */
    @Override
    @Transactional
    public void run(@NotNull String... args) throws Exception {

        System.out.println(userDefaultProperties.getPassword());
        if (!configurationRepository.existsByType(ConfigurationTypes.APP_FIRST_INITIALIZER)) {
            saveDefaultUser();
        }

        for (ConfigurationTypes type: ConfigurationTypes.values()) {
            if (!configurationRepository.existsByType(type)) {
                ConfigurationEntity configuration = new ConfigurationEntity();
                configuration.setType(type);
                configuration.setValue(getDefaultValue(type));
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

    private void saveDefaultUser() {
        UserEntity userDefault = new UserEntity();
        userDefault.setFirstName(userDefaultProperties.getName());
        userDefault.setLastName(userDefaultProperties.getLastName());
        userDefault.setEmail(userDefaultProperties.getEmail());
        userDefault.setPassword(passwordEncoder.encode(userDefaultProperties.getPassword()));
        userDefault.setRoles(Roles.ADMIN);
        userDefault.setIsActive(true);

        userRepository.save(userDefault);
    }

}
