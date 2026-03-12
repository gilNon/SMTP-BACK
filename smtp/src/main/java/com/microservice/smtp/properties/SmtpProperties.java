package com.microservice.smtp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "smtp-server")
@Component
@Getter
@Setter
public class SmtpProperties {

    private String user;
    private String host;
    private String ssl;
    private String tls;
    private Integer port;
}
