package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.request.UserUpdateRequest;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

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
     * @param pageable the page request
     * @return the list of users
     */
    PagesDataResponse<List<UserResponse>> getAllUsers(Pageable pageable);

    /**
     * Updates a user by email.
     * @param email the email of the user
     * @param userRequest the user response
     * @return the updated user response
     */
    UserResponse updateUserByEmail(String email, UserUpdateRequest userRequest);

    /**
     * Deletes a user by email.
     * @param idUser the email of the user
     */
    void deleteUserById(UUID idUser);

    UserResponse getUserByID(UUID idUser);

}
