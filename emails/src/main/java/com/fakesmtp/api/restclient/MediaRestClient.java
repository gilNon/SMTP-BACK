package com.fakesmtp.api.restclient;

import com.fakesmtp.api.restclient.response.MediaResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient( name = "media-microservice")
public interface MediaRestClient {

    @GetMapping(value = "/api/v1/medias/{idEmail}")
    List<MediaResponseClientDto> getMediasByIdEmail(@PathVariable(value = "idEmail") UUID idEmail);

}
