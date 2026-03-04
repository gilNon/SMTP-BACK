package com.microservice.auth.controller;

import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaResponseDto> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idEmail") UUID idEmail
    ) {
        return new ResponseEntity<>(mediaService.uploadMedia(file, idEmail), HttpStatus.OK);
    }
}
