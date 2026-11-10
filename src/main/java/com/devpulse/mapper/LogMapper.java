package com.devpulse.mapper;

import com.devpulse.dto.LogResponse;
import com.devpulse.entity.LogEntry;

public class LogMapper {

    private LogMapper() {
    }

    public static LogResponse toResponse(LogEntry logEntry) {

        return new LogResponse(
                logEntry.getId(),
                logEntry.getLevel(),
                logEntry.getService(),
                logEntry.getMessage(),
                logEntry.getStackTrace(),
                logEntry.getTimestamp()
        );
    }
}