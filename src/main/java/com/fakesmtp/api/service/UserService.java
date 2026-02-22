package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for user operations.
 * @author Gilberto Vazquez
 */
public interface UserService {

    /**
     * Gets a user by email.
     * @param email the email of the user
     * @return the user response
     */
    UserResponse getUserByEmail(String email);

    /**
     * Gets all users by application.
     * @param userName the username of the user
     * @param pageable the page request
     * @return the list of users
     */
    PagesDataResponse<List<UserResponse>> getAllUsersByApplication(String userName, Pageable pageable);
}
