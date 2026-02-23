package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.response.ApplicationResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.enums.MessageErrors;
import com.fakesmtp.api.exception.GeneralException;
import com.fakesmtp.api.mapper.ApplicationMapper;
import com.fakesmtp.api.model.ApplicationEntity;
import com.fakesmtp.api.repository.ApplicationRepository;
import com.fakesmtp.api.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    /**
     * Method to retrieve an application by its ID.
     * @param idApplication UUID application fetch.
     * @return ApplicationResponse object.
     */
    @Override
    public ApplicationResponse getApplicationById(UUID idApplication) {

        ApplicationEntity application = applicationRepository.findById(idApplication).orElseThrow(() ->
                new GeneralException(HttpStatus.NOT_FOUND,
                        MessageErrors.APPLICATION_NOT_FOUND.getMessage())
        );
        return ApplicationMapper.toApplicationResponse(application);
    }

    /**
     * Method to retrieve a list of applications.
     * @param pageable request.
     * @return List of ApplicationResponse objects.
     */
    @Override
    public PagesDataResponse<List<ApplicationResponse>> getAllApplications(Pageable pageable) {
        return ApplicationMapper.getPageApplications(applicationRepository.findAll(pageable));
    }

    /**
     * Method to delete an application by its ID.
     *
     * @param idApplication UUID application delete.
     */
    @Override
    public void deleteApplication(UUID idApplication) {
        ApplicationEntity application = applicationRepository.findById(idApplication).orElseThrow(() ->
            new GeneralException(HttpStatus.NOT_FOUND,
                    MessageErrors.APPLICATION_NOT_FOUND.getMessage())
        );

       application.setActive(false);
       applicationRepository.save(application);
    }
}
