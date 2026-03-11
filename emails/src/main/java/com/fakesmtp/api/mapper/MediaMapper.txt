package com.fakesmtp.api.mapper;

import com.fakesmtp.api.dto.response.MediaResponse;
import com.fakesmtp.api.model.MediaEntity;

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
     * @param mediaEntity the MediaEntity to convert
     * @return the corresponding MediaResponse
     */
    public static MediaResponse toMediaResponse(MediaEntity mediaEntity) {

        return new MediaResponse(
                mediaEntity.getIdMedia(),
                mediaEntity.getMediaURL(),
                mediaEntity.getFileName(),
                mediaEntity.getCreatedAt(),
                mediaEntity.getUpdatedAt()
        );
    }
}
