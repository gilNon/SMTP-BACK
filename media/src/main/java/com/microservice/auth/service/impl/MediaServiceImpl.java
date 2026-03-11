package com.microservice.auth.service.impl;

import com.microservice.auth.config.MinioConfigurationProperties;
import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.entity.MediaEntity;
import com.microservice.auth.exception.GeneralException;
import com.microservice.auth.mapper.MediaMapper;
import com.microservice.auth.repository.MediaRepository;
import com.microservice.auth.service.MediaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MinioClientService minioClientService;
    private final MediaRepository mediaRepository;
    private final MinioConfigurationProperties minioProperties;
    private final MediaMapper mediaMapper;

    @Override
    @Transactional
    public void uploadMedia(MultipartFile file, UUID idEmail) {
        if(file.isEmpty()) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setIdEmail(idEmail);
        mediaEntity.setFileName(file.getOriginalFilename());
        mediaEntity.setFolder(UUID.randomUUID().toString());
        mediaEntity.setCreatedAt(Instant.now());
        mediaEntity.setUpdatedAt(Instant.now());

        mediaEntity = mediaRepository.save(mediaEntity);

        String url = "/" + mediaEntity.getFolder() + "/" + mediaEntity.getFileName();
        log.info("URL: {}", url);

        minioClientService.putObject(file, url);
    }

    @Override
    public List<MediaResponseDto> getAllMediaByEmail(UUID idEmail) {
        List<MediaEntity> mediaEntities = mediaRepository.findAllByIdEmail(idEmail);
        return mediaEntities.stream().map(mediaMapper::toMediaResponse).toList();
    }
}
