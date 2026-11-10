package com.devpulse.ai.dto;

public class AIAnalysisRequest {

    private Long projectId;

    public AIAnalysisRequest() {
    }

    public AIAnalysisRequest(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}