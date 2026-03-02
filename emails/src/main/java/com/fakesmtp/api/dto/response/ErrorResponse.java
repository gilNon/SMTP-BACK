package com.fakesmtp.api.dto.response;

import java.time.Instant;
import java.util.List;

/**
 * Class that represents an error response.
 * @author Gilberto Vazquez
 */
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        List<Detail> details
) {

    public record Detail(
            String field,
            String message
    ) {}

}
