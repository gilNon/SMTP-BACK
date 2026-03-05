package com.microservice.auth.mapper;

import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.entity.MediaEntity;
import com.microservice.auth.service.impl.MinioClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaMapper {

    private final MinioClientService minioClientService;

    public MediaEntity toMediaEntity(String fileName, UUID idEmail) {
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setFolder(UUID.randomUUID().toString());
        mediaEntity.setFileName(fileName);
        mediaEntity.setIdEmail(idEmail);
        return mediaEntity;
    }

    public MediaResponseDto toMediaResponse(MediaEntity mediaEntity) {
        return MediaResponseDto.builder()
                .folder(mediaEntity.getFolder())
                .fileName(mediaEntity.getFileName())
                .idEmail(mediaEntity.getIdEmail())
                .idMedia(mediaEntity.getIdMedia())
                .createdAt(mediaEntity.getCreatedAt())
                .updatedAt(mediaEntity.getUpdatedAt())
                .url(minioClientService.getFilePreSigned(mediaEntity.getFolder() + "/" + mediaEntity.getFileName()))
                .build();
    }

}
