package com.devpulse.dto;

import com.devpulse.enums.LogLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LogRequest {

    @NotNull(message = "Log level is required")
    private LogLevel level;

    @NotBlank(message = "Service name is required")
    private String service;

    @NotBlank(message = "Message is required")
    private String message;

    private String stackTrace;

    public LogRequest() {
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}