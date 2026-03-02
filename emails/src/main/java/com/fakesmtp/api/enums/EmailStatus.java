package com.fakesmtp.api.enums;

import lombok.Getter;

/**
 * Enumeration representing the status of an email.
 * @author Gilberto Vazquez
 */
@Getter
public enum EmailStatus {
    RECEIVED,
    READ,
    DELETED
}
