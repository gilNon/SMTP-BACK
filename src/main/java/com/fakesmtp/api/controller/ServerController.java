package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.response.ConfigurationResponse;
import com.fakesmtp.api.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling application-related operations.
 * @author Gilberto Vazquez
 */
@RestController
@RequestMapping("/server")
@AllArgsConstructor
public class ServerController {

    private final ServerService serverService;

    /**
     * Controller method to delete an application by its ID.
     */
    @GetMapping("/info-server")
    public ResponseEntity<List<ConfigurationResponse>> getInfoServer() {

        return new ResponseEntity<>(serverService.getInfoServerSMTP(), HttpStatus.OK);
    }
}
