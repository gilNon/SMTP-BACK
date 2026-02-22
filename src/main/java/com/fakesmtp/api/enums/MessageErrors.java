package com.fakesmtp.api.enums;

import lombok.Getter;

/**
 * Enum for error messages.
 * @author Gilberto Vazquez
 */
@Getter
public enum MessageErrors {
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USER_NOT_FOUND("User not found"),
    EMAIL_NOT_FOUND("Email not found"),
    INVALID_CREDENTIALS("Invalid or missing token"),
    ACCESS_DENIED("Access denied");

    private final String message;

    MessageErrors(String message) {
        this.message = message;
    }

}
