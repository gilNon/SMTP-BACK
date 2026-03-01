package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.dto.response.ConfigurationResponse;

import com.fakesmtp.api.mapper.ConfigurationMapper;
import com.fakesmtp.api.model.ConfigurationEntity;
import com.fakesmtp.api.repository.ConfigurationRepository;
import com.fakesmtp.api.service.ServerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ConfigurationRepository configurationRepository;

    /**
     * @return
     */
    @Override
    public List<ConfigurationResponse> getInfoServerSMTP() {
        List<ConfigurationEntity> configurationEntities =
                configurationRepository.findAll();


        return configurationEntities.stream()
                .map(ConfigurationMapper::toConfigurationResponse)
                .toList();
    }
}
