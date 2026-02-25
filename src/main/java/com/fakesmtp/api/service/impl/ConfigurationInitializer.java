package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.enums.ConfigurationTypes;
import com.fakesmtp.api.model.ConfigurationEntity;
import com.fakesmtp.api.repository.ConfigurationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationInitializer implements CommandLineRunner {

    
    private final ConfigurationRepository configurationRepository;

    private final String userSMTP;
    private final String passwordSMTP;
    private final String hostSMTP;
    private final String sslSMTP;
    private final String tlsSMTP;
    private final String portSMTP;

    public ConfigurationInitializer(ConfigurationRepository configurationRepository,
                                    String userSMTP,
                                    String passwordSMTP,
                                    String hostSMTP,
                                    String sslSMTP,
                                    String tlsSMTP,
                                    String portSMTP) {
        this.configurationRepository = configurationRepository;
        this.userSMTP = userSMTP;
        this.passwordSMTP = passwordSMTP;
        this.hostSMTP = hostSMTP;
        this.sslSMTP = sslSMTP;
        this.tlsSMTP = tlsSMTP;
        this.portSMTP = portSMTP;
    }
    
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
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
            case PORT_SMTP -> portSMTP;
            case HOST_SMTP -> hostSMTP;
            case USER_SMTP -> userSMTP;
            case PASSWORD_SMTP -> passwordSMTP;
            case SSL_SMTP -> sslSMTP;
            case TLS_SMTP -> tlsSMTP;
        };
    }
}
