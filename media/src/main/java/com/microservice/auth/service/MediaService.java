package com.microservice.auth.service;

import com.microservice.auth.controller.response.MediaResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MediaService {

    void uploadMedia(MultipartFile file, UUID idEmail);

    List<MediaResponseDto> getAllMediaByEmail(UUID idEmail);

}
