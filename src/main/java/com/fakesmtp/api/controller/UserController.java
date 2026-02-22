package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import com.fakesmtp.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling user API requests.
 * @author Gilberto Vazquez
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Gets the current user.
     * @param userDetails the user details
     * @return the current user
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return new ResponseEntity<>(
                userService.getUserByEmail(userDetails.getUsername()),
                HttpStatus.OK
        );
    }

    /**
     * Gets all users by application.
     * @param userDetails the user details
     * @param pageable the page request
     * @return the list of users
     */
    @GetMapping()
    public ResponseEntity<PagesDataResponse<List<UserResponse>>> getAllUsersByApplication(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return new ResponseEntity<>(
                userService.getAllUsersByApplication(userDetails.getUsername(), pageable),
                HttpStatus.OK
        );
    }
}
