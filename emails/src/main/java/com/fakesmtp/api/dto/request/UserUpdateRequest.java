package com.fakesmtp.api.dto.request;

import jakarta.validation.constraints.Email;

/**
 * User update request.
 * @author Gilberto Vazquez
 */
public record UserUpdateRequest(
        String firstName,
        String lastName,
        @Email
        String email
) {
}
