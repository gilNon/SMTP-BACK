package com.fakesmtp.api.service;

import com.fakesmtp.api.dto.response.ConfigurationResponse;
import com.fakesmtp.api.dto.response.ServerResponse;

import java.util.List;

public interface ServerService {

    List<ConfigurationResponse> getInfoServerSMTP();

}
