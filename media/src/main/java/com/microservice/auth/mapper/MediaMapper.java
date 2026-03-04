package com.microservice.auth.mapper;

import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.entity.MediaEntity;

import java.util.UUID;

public class MediaMapper {

    private MediaMapper() {
        // Private constructor for utility class.
    }

    MediaEntity toMediaEntity(String fileName, UUID idEmail) {
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setFolder(UUID.randomUUID().toString());
        mediaEntity.setFileName(fileName);
        mediaEntity.setIdEmail(idEmail);
        return mediaEntity;
    }

    MediaResponseDto toMediaResponse(MediaEntity mediaEntity) {
        MediaResponseDto mediaResponseDto = MediaResponseDto.builder()
                .folder(mediaEntity.getFolder())
                .fileName(mediaEntity.getFileName())
                .idEmail(mediaEntity.getIdEmail())
                .idMedia(mediaEntity.getIdMedia())
                .createdAt(mediaEntity.getCreatedAt())
                .updatedAt(mediaEntity.getUpdatedAt())
                .build();
    }

}
