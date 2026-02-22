package com.fakesmtp.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * Generic Data Transfer Object for listing email responses with a timestamp.
 * @author Gilberto Vazquez
 */
public record PagesDataResponse<T>(
        T data,
        Instant timestamp,
        @JsonProperty("pagination")
        PaginationResponse paginationResponse
) {}
