package com.devpulse.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.devpulse.enums.LogLevel;

import java.util.List;

import com.devpulse.dto.LogRequest;
import com.devpulse.dto.LogResponse;

public interface LogService {

    LogResponse saveLog(String apiKey, LogRequest request);

    Page<LogResponse> getLogsByProject(
            Long projectId,
            LogLevel level,
            String service,
            Pageable pageable);

}