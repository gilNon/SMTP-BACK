package com.fakesmtp.api.controller;

import com.fakesmtp.api.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller class for handling application-related operations.
 * @author Gilberto Vazquez
 */
@RestController
@RequestMapping("/server")
@AllArgsConstructor
public class ServerController {

    private final ServerService applicationService;

    /**
     * Controller method to delete an application by its ID.
     * @param idApplication UUID application delete.
     */
    @DeleteMapping("/info-server")
    public ResponseEntity<Void> getInfoServer(
            @PathVariable UUID idApplication
    ) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
