package com.fakesmtp.api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user-default")
@Component
@Getter
@Setter
public class UserDefaultProperties {

    private String email;
    private String password;
    private String name;
    private String lastName;

}
