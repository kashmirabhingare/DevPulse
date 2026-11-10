package com.devpulse.ai.provider;

import com.devpulse.ai.dto.AIAnalysisResponse;

public interface AIProvider {

    AIAnalysisResponse analyze(String prompt);

}