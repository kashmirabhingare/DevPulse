package com.devpulse.dto;

import java.time.LocalDateTime;

import com.devpulse.enums.LogLevel;

public class LogResponse {

    private Long id;
    private LogLevel level;
    private String service;
    private String message;
    private String stackTrace;
    private LocalDateTime timestamp;

    public LogResponse() {
    }

    public LogResponse(Long id,
                       LogLevel level,
                       String service,
                       String message,
                       String stackTrace,
                       LocalDateTime timestamp) {

        this.id = id;
        this.level = level;
        this.service = service;
        this.message = message;
        this.stackTrace = stackTrace;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getService() {
        return service;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}