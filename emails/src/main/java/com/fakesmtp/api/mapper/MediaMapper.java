package com.fakesmtp.api.mapper;

import com.fakesmtp.api.dto.response.MediaResponse;
import com.fakesmtp.api.restclient.response.MediaResponseClientDto;

/**
 * Mapper class for converting MediaEntity to MediaResponse.
 * @author Gilberto Vazquez
 */
public class MediaMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private MediaMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a MediaEntity to a MediaResponse.
     *
     * @return the corresponding MediaResponse
     */
    public static MediaResponse toMediaResponse(MediaResponseClientDto mediaResponseClientDto) {

        return new MediaResponse(
                mediaResponseClientDto.idMedia(),
                mediaResponseClientDto.url(),
                mediaResponseClientDto.fileName(),
                mediaResponseClientDto.createdAt(),
                mediaResponseClientDto.updatedAt()
        );
    }
}
