package com.fakesmtp.api.config.smtp;

import com.fakesmtp.api.enums.ConfigurationTypes;
import com.fakesmtp.api.model.ConfigurationEntity;
import com.fakesmtp.api.repository.ConfigurationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.auth.LoginFailedException;
import org.subethamail.smtp.auth.UsernamePasswordValidator;

/**
 * Simple authentication validator.
 * @author Gilberto Vazquez
 */
@Component
@AllArgsConstructor
@Slf4j
public class SimpleAuthValidatorImpl implements UsernamePasswordValidator {

    private final ConfigurationRepository configurationRepository;

    /**
     * Validates the user credentials.
     * @param username username.
     * @param password password.
     * @param messageContext message context.
     * @throws LoginFailedException if the authentication fails.
     */
    @Override
    public void login(String username, String password, MessageContext messageContext) throws LoginFailedException {
        ConfigurationEntity userConfig = configurationRepository
                .findByTypeAndValue(ConfigurationTypes.USER_SMTP, username)
                .orElseThrow(LoginFailedException::new);

        ConfigurationEntity passwordConfig = configurationRepository
                .findByApplicationAndType(userConfig.getApplication(), ConfigurationTypes.PASSWORD_SMTP)
                .orElseThrow(LoginFailedException::new);

        if (passwordConfig.getValue() != null && passwordConfig.getValue().equals(password)) {
           log.info("Authenticated successfully");
        } else {
            log.error("Invalid authentication !");
            throw new LoginFailedException();
        }
    }

}
