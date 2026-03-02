package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.UserResponse;
import com.fakesmtp.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling user API requests.
 * @author Gilberto Vazquez
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
       return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID idUser) {

        return new ResponseEntity<>(userService.getUserByID(idUser), HttpStatus.OK);
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID idUser) {
        userService.deleteUserById(idUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
