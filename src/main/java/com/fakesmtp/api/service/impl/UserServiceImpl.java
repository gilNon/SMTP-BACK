package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.config.security.JwtService;
import com.fakesmtp.api.dto.request.UserUpdateRequest;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.UserMapper;
import com.fakesmtp.api.model.UserEntity;
import com.fakesmtp.api.repository.UserRepository;
import com.fakesmtp.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for user operations.
 * @author Gilberto Vazquez
 */
@AllArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    /**
     * Gets a user by email.
     * @param email the email of the user
     * @return the user response
     */
    @Override
    public UserResponse getUserByEmail(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage()));

        return UserMapper.toUserResponse(user) ;
    }

    /**
     * Gets all users by application.
     * @param userName the username of the user
     * @param pageable the page request
     * @return the list of users
     */
    @Override
    public PagesDataResponse<List<UserResponse>> getAllUsers(String userName, Pageable pageable) {
       return null;
    }

    /**
     * Updates a user by email.
     *
     * @param emailUser        the email of the user
     * @param userResponse the user request
     * @return the updated user response
     */
    @Override
    public UserResponse updateUserByEmail(String emailUser, UserUpdateRequest userResponse) {
        UserEntity user = userRepository.findByEmail(emailUser)
                .orElseThrow(() -> new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage()));

        return UserMapper.toUserResponse(userRepository.save(UserMapper.toEntity(userResponse, user)));
    }

    /**
     * Deletes a user by email.
     *
     * @param emailUser the email of the user
     */
    @Override
    public void deleteUserByEmail(String emailUser) {
        UserEntity user = userRepository.findByEmail(emailUser)
                .orElseThrow(() -> new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.USER_NOT_FOUND.getMessage()));

        user.setIsActive(false);
        userRepository.save(user);
    }
}
