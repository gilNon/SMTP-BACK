package com.fakesmtp.api.mapper;

import com.fakesmtp.api.dto.request.RegisterRequest;
import com.fakesmtp.api.dto.request.UserUpdateRequest;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.PaginationResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import com.fakesmtp.api.enums.Roles;
import com.fakesmtp.api.model.UserEntity;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

/**
 * Mapper class for mapping user entities to register requests.
 * @author Gilberto Vazquez
 */
public class UserMapper {

    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a RegisterRequest to a UserEntity.
     * @param registerRequest the register request
     * @return the user entity
     */
    public static UserEntity toEntity(RegisterRequest registerRequest) {
        UserEntity user = new UserEntity();
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setEmail(registerRequest.email());
        user.setRoles(Roles.USER);
        user.setIsActive(true);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        return user;
    }

    /**
     * Converts a UserEntity to a UserResponse.
     * @param userEntity the user entity
     * @return the user response
     */
    public static UserResponse toUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRoles().name(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    /**
     * Converts a Page of UserEntity to a PagesDataResponse of UserResponse.
     * @param page the page of user entities
     * @return the pages data response of user responses
     */
    public static PagesDataResponse<List<UserResponse>> toUsersListResponse(
            Page<UserEntity> page) {

        List<UserResponse> list = page.getContent().stream()
                .map(UserMapper::toUserResponse)
                .toList();

        PaginationResponse pagination = new PaginationResponse(page);
        return new PagesDataResponse<>(list, Instant.now(), pagination);
    }

    /**
     * Converts a UserUpdateRequest to a UserEntity.
     * @param userUpdateRequest the user update request
     * @param userEntity the user entity
     * @return the user entity
     */
    public static UserEntity toEntity(UserUpdateRequest userUpdateRequest, UserEntity userEntity) {
        if(userUpdateRequest.email() != null) {
            userEntity.setEmail(userUpdateRequest.email());
        }
        if(userUpdateRequest.firstName() != null) {
            userEntity.setFirstName(userUpdateRequest.firstName());
        }
        if(userUpdateRequest.lastName() != null) {
            userEntity.setLastName(userUpdateRequest.lastName());
        }
        return userEntity;
    }

}
