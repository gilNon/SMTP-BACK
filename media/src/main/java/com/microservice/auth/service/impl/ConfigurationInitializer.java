package com.microservice.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigurationInitializer implements CommandLineRunner {

    private final MinioClientService minioClientService;
    
    /**
     * @param args
     * @throws Exception
     */
    @Override
    @Transactional
    public void run(@NotNull String... args) throws Exception {
        minioClientService.createBucket();
    }

}
