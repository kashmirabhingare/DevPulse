package com.devpulse.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.devpulse.enums.LogLevel;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.devpulse.dto.LogRequest;
import com.devpulse.dto.LogResponse;
import com.devpulse.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public LogResponse saveLog(
            @RequestHeader("X-API-KEY") String apiKey,
            @Valid @RequestBody LogRequest request) {

        return logService.saveLog(apiKey, request);
    }

    @GetMapping("/{projectId}")
    public Page<LogResponse> getLogs(
            @PathVariable Long projectId,

            @RequestParam(required = false)
            LogLevel level,

            @RequestParam(required = false)
            String service,

            Pageable pageable) {

        return logService.getLogsByProject(
                projectId,
                level,
                service,
                pageable);
    }
}