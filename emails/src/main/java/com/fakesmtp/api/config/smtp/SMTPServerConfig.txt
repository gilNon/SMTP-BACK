package com.fakesmtp.api.config.smtp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.SmartLifecycle;
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory;
import org.subethamail.smtp.auth.UsernamePasswordValidator;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

/**
 * SMTP server configuration.
 * @author Gilberto Vazquez
 */
@Configuration
public class SMTPServerConfig {

    @Bean
    public SMTPServer smtpServer(MessageHandlerFactory messageHandlerFactory,
                                 UsernamePasswordValidator authValidator) {
        var easyAuth = new EasyAuthenticationHandlerFactory(authValidator);

        return SMTPServer
                .port(25000)
                .messageHandlerFactory(messageHandlerFactory)
                .requireAuth(true)
                .authenticationHandlerFactory(easyAuth)
                .build();
    }

    @Bean
    public SmartLifecycle smtpServerLifecycle(SMTPServer smtpServer) {
        return new SmartLifecycle() {
            private volatile boolean running = false;

            @Override
            public void start() {
                smtpServer.start();
                running = true;
            }

            @Override
            public void stop() {
                smtpServer.stop();
                running = false;
            }

            @Override
            public boolean isRunning() {
                return running;
            }

            @Override
            public boolean isAutoStartup() {
                return true;
            }
        };
    }
}
