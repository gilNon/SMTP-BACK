package com.microservice.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for general errors.
 * @author Gilberto Vazquez
 */
@Getter
public class GeneralException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Constructor.
     * @param status status.
     * @param message message.
     */
    public GeneralException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
