package com.fakesmtp.api.controller;

import com.fakesmtp.api.dto.response.ApplicationResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for handling application-related operations.
 * @author Gilberto Vazquez
 */
@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * Controller method to retrieve a list of applications.
     * @param pageable Pageable request.
     * @return List of applications.
     */
    @GetMapping
    public ResponseEntity<PagesDataResponse<List<ApplicationResponse>>> getApplications(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return new ResponseEntity<>(applicationService.getAllApplications(pageable), HttpStatus.OK);
    }

    /**
     * Controller method to retrieve an application by its ID.
     * @param idApplication UUID application fetch.
     * @return the application response.
     */
    @GetMapping("/{idApplication}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable UUID idApplication
    ) {
        return new ResponseEntity<>(applicationService.getApplicationById(idApplication), HttpStatus.OK);
    }

    /**
     * Controller method to delete an application by its ID.
     * @param idApplication UUID application delete.
     */
    @DeleteMapping("/{idApplication}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable UUID idApplication
    ) {
        applicationService.deleteApplication(idApplication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
