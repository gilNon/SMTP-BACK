package com.microservice.auth.controller;

import com.microservice.auth.controller.response.MediaResponseDto;
import com.microservice.auth.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadMedia(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("idEmail") UUID idEmail
    ) {

        files.forEach(file -> mediaService.uploadMedia(file, idEmail));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{idEmail}")
    public ResponseEntity<List<MediaResponseDto>> getAllMediaByEmail(@PathVariable UUID idEmail) {
        return ResponseEntity.ok(mediaService.getAllMediaByEmail(idEmail));
    }
}
