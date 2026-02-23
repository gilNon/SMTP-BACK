package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.response.ApplicationResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing application operations.
 * Provides methods to retrieve application data.
 * @author Gilberto Vazquez
 */
public interface ApplicationService {

    /**
     * Method to retrieve an application by its ID.
     * @param idApplication UUID application fetch.
     * @return ApplicationResponse object.
     */
    ApplicationResponse getApplicationById(UUID idApplication);

    /**
     * Method to retrieve a list of applications.
     * @param pageable request.
     * @return List of ApplicationResponse objects.
     */
    PagesDataResponse<List<ApplicationResponse>> getAllApplications(Pageable pageable);

    /**
     * Method to delete an application by its ID.
     * @param idApplication UUID application delete.
     */
    void deleteApplication(UUID idApplication);

}
