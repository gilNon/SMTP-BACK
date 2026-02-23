package com.fakesmtp.api.mapper;

import com.fakesmtp.api.dto.response.ApplicationResponse;
import com.fakesmtp.api.dto.response.PagesDataResponse;
import com.fakesmtp.api.dto.response.PaginationResponse;
import com.fakesmtp.api.model.ApplicationEntity;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

/**
 * Mapper class for converting ApplicationEntity to ApplicationResponse.
 * @author Gilberto Vazquez
 */
public class ApplicationMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private ApplicationMapper() {
        //private constructor to prevent instantiation
    }

    /**
     * Converts an ApplicationEntity to an ApplicationResponse.
     * @param application the ApplicationEntity to convert
     * @return the ApplicationResponse
     */
    public static ApplicationResponse toApplicationResponse(ApplicationEntity application) {
        return new ApplicationResponse(
                application.getIdApplication(),
                application.getActive(),
                application.getUsers().stream().map(UserMapper::toUserResponse).toList(),
                application.getConfigurations().stream().map(ConfigurationMapper::toConfigurationResponse).toList(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }

    /**
     * Converts a Page of ApplicationEntity to a PagesDataResponse of ApplicationResponse.
     * @param page the Page of ApplicationEntity to convert
     * @return the PagesDataResponse of ApplicationResponse
     */
    public static PagesDataResponse<List<ApplicationResponse>> getPageApplications(Page<ApplicationEntity> page) {
        List<ApplicationResponse> applications = page.getContent()
                .stream()
                .map(ApplicationMapper::toApplicationResponse).toList();

        PaginationResponse paginationResponse = new PaginationResponse(page);

        return new PagesDataResponse<>(applications, Instant.now(), paginationResponse );
    }

}
