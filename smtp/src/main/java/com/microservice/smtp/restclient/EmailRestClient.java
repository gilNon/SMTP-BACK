package com.microservice.smtp.restclient;

import com.microservice.smtp.restclient.request.EmailRequestDto;
import com.microservice.smtp.restclient.response.EmailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-microservice")
public interface EmailRestClient {

    @PostMapping("/api/v1/emails")
    EmailResponseDto saveEmail(@RequestBody EmailRequestDto emailRequestDto);
}
