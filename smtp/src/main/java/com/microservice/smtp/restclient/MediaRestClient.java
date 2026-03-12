package com.microservice.smtp.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient( name = "media-microservice")
public interface MediaRestClient {

    @PostMapping(value = "/api/v1/medias", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void saveMedia( @RequestPart("files") List<MultipartFile> files,
                    @RequestPart("idEmail") String idEmail);
}
