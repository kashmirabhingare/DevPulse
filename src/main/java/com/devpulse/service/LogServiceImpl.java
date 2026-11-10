package com.devpulse.service;

import java.time.LocalDateTime;
import com.devpulse.authorization.ProjectAccessService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.devpulse.enums.LogLevel;
import com.devpulse.specification.LogSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.devpulse.dto.LogRequest;
import com.devpulse.dto.LogResponse;
import com.devpulse.entity.LogEntry;
import com.devpulse.entity.Project;
import com.devpulse.exception.ResourceNotFoundException;
import com.devpulse.mapper.LogMapper;
import com.devpulse.repository.LogRepository;
import com.devpulse.repository.ProjectRepository;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ProjectRepository projectRepository;
    private final ProjectAccessService projectAccessService;

    public LogServiceImpl(LogRepository logRepository,
            ProjectRepository projectRepository,
            ProjectAccessService projectAccessService) {

    	this.logRepository = logRepository;
    	this.projectRepository = projectRepository;
    	this.projectAccessService = projectAccessService;
}

    @Override
    public LogResponse saveLog(String apiKey, LogRequest request) {

        Project project = projectRepository.findByApiKey(apiKey)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid API Key"));

        LogEntry logEntry = new LogEntry();

        logEntry.setLevel(request.getLevel());
        logEntry.setService(request.getService());
        logEntry.setMessage(request.getMessage());
        logEntry.setStackTrace(request.getStackTrace());
        logEntry.setTimestamp(LocalDateTime.now());
        logEntry.setProject(project);

        LogEntry savedLog = logRepository.save(logEntry);

        return LogMapper.toResponse(savedLog);
    }

    @Override
    public Page<LogResponse> getLogsByProject(
            Long projectId,
            LogLevel level,
            String service,
            Pageable pageable) {

        Project project =
                projectAccessService.getAuthorizedProject(projectId);

        Specification<LogEntry> specification =
                LogSpecification.hasProjectId(project.getId())
                        .and(LogSpecification.hasLevel(level))
                        .and(LogSpecification.hasService(service));

        return logRepository
                .findAll(specification, pageable)
                .map(LogMapper::toResponse);
    }

}