package com.devpulse.service;

import org.springframework.stereotype.Service;

import com.devpulse.authorization.ProjectAccessService;
import com.devpulse.dto.DashboardResponse;
import com.devpulse.entity.LogEntry;
import com.devpulse.entity.Project;
import com.devpulse.enums.LogLevel;
import com.devpulse.repository.LogRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final LogRepository logRepository;
    private final ProjectAccessService projectAccessService;

    public DashboardServiceImpl(LogRepository logRepository,
                                ProjectAccessService projectAccessService) {

        this.logRepository = logRepository;
        this.projectAccessService = projectAccessService;
    }

    @Override
    public DashboardResponse getDashboard(Long projectId) {

        Project project =
                projectAccessService.getAuthorizedProject(projectId);

        long totalLogs =
                logRepository.countByProjectId(project.getId());

        long errorLogs =
                logRepository.countByProjectIdAndLevel(
                        project.getId(),
                        LogLevel.ERROR);

        long warnLogs =
                logRepository.countByProjectIdAndLevel(
                        project.getId(),
                        LogLevel.WARN);

        long infoLogs =
                logRepository.countByProjectIdAndLevel(
                        project.getId(),
                        LogLevel.INFO);

        long debugLogs =
                logRepository.countByProjectIdAndLevel(
                        project.getId(),
                        LogLevel.DEBUG);

        double errorPercentage =
                totalLogs == 0
                        ? 0
                        : (errorLogs * 100.0) / totalLogs;

        String healthStatus;

        if (errorPercentage < 5) {
            healthStatus = "HEALTHY";
        } else if (errorPercentage < 15) {
            healthStatus = "WARNING";
        } else {
            healthStatus = "CRITICAL";
        }

        LogEntry latestLog =
                logRepository.findTopByProjectIdOrderByTimestampDesc(
                        project.getId());

        String mostActiveService =
                logRepository.findMostActiveService(project.getId());

        return new DashboardResponse(
                totalLogs,
                errorLogs,
                warnLogs,
                infoLogs,
                debugLogs,
                errorPercentage,
                healthStatus,
                mostActiveService,
                latestLog != null ? latestLog.getTimestamp() : null
        );
    }
}