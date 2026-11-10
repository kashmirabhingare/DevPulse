package com.devpulse.ai.controller;

import org.springframework.web.bind.annotation.*;

import com.devpulse.ai.dto.AIAnalysisRequest;
import com.devpulse.ai.dto.AIAnalysisResponse;
import com.devpulse.ai.service.AIAnalysisService;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIAnalysisService aiAnalysisService;

    public AIController(AIAnalysisService aiAnalysisService) {
        this.aiAnalysisService = aiAnalysisService;
    }

    @PostMapping("/analyze")
    public AIAnalysisResponse analyze(
            @RequestBody AIAnalysisRequest request) {

        return aiAnalysisService.analyzeProject(
                request.getProjectId());
    }
}