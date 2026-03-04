package com.microservice.auth.service.impl;

import com.microservice.auth.config.MinioConfigurationProperties;
import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.entity.MediaEntity;
import com.microservice.auth.exception.GeneralException;
import com.microservice.auth.repository.MediaRepository;
import com.microservice.auth.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MinioClientService minioClientService;
    private final MediaRepository mediaRepository;
    private final MinioConfigurationProperties minioProperties;

    @Override
    public MediaResponseDto uploadMedia(MultipartFile file, UUID idEmail) {
        if(file.isEmpty()) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setIdEmail(idEmail);
        mediaEntity.setFileName(file.getOriginalFilename());
        mediaEntity.setFolder(minioProperties.getBucketName());
        mediaEntity.setCreatedAt(Instant.now());
        mediaEntity.setUpdatedAt(Instant.now());

        minioClientService.putObject(file, minioProperties.getBucketName(), fileName);





        return null;
    }

    @Override
    public List<MediaResponseDto> getAllMediaByEmail(UUID idEmail) {
        return List.of();
    }
}
