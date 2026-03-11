package com.microservice.smtp.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient( name = "media-microservice")
public interface MediaRestClient {

    @PostMapping("/medias/{idEmail}")
    Void saveMedia( @RequestParam("files") List<MultipartFile> files,
                           @RequestParam("idEmail") UUID idEmail);
}
