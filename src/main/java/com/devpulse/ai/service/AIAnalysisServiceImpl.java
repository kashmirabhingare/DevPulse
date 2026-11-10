package com.devpulse.ai.service;

import org.springframework.stereotype.Service;
import com.devpulse.ai.prompt.PromptBuilder;
import com.devpulse.ai.dto.AIAnalysisResponse;
import com.devpulse.ai.provider.AIProvider;
import com.devpulse.authorization.ProjectAccessService;
import com.devpulse.dto.DashboardResponse;
import com.devpulse.entity.LogEntry;
import com.devpulse.entity.Project;
import com.devpulse.repository.LogRepository;
import com.devpulse.service.DashboardService;

import java.util.List;

@Service
public class AIAnalysisServiceImpl implements AIAnalysisService {

    private final AIProvider aiProvider;
    private final DashboardService dashboardService;
    private final LogRepository logRepository;
    private final ProjectAccessService projectAccessService;
    

    public AIAnalysisServiceImpl(
            AIProvider aiProvider,
            DashboardService dashboardService,
            LogRepository logRepository,
            ProjectAccessService projectAccessService) {

        this.aiProvider = aiProvider;
        this.dashboardService = dashboardService;
        this.logRepository = logRepository;
        this.projectAccessService = projectAccessService;
    }

    @Override
    public AIAnalysisResponse analyzeProject(Long projectId) {

        // We'll implement this in the next step

    	Project project =
    	        projectAccessService.getAuthorizedProject(projectId);

    	DashboardResponse dashboard =
    	        dashboardService.getDashboard(project.getId());

    	List<LogEntry> recentLogs =
    	        logRepository.findTop100ByProjectIdOrderByTimestampDesc(
    	                project.getId());

    	String prompt =
    	        PromptBuilder.buildPrompt(
    	                dashboard,
    	                recentLogs);

    	return aiProvider.analyze(prompt);
    }
}