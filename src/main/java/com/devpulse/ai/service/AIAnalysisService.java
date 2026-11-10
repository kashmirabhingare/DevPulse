package com.devpulse.ai.service;

import com.devpulse.ai.dto.AIAnalysisResponse;

public interface AIAnalysisService {

    AIAnalysisResponse analyzeProject(Long projectId);

}