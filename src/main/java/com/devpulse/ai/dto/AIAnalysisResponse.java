package com.devpulse.ai.dto;

import java.util.List;

public class AIAnalysisResponse {

    private String summary;

    private String rootCause;

    private String severity;

    private int healthScore;

    private List<String> suggestedFixes;

    private List<String> preventionTips;

    public AIAnalysisResponse() {
    }

    public AIAnalysisResponse(String summary,
                              String rootCause,
                              String severity,
                              int healthScore,
                              List<String> suggestedFixes,
                              List<String> preventionTips) {

        this.summary = summary;
        this.rootCause = rootCause;
        this.severity = severity;
        this.healthScore = healthScore;
        this.suggestedFixes = suggestedFixes;
        this.preventionTips = preventionTips;
    }

    public String getSummary() {
        return summary;
    }

    public String getRootCause() {
        return rootCause;
    }

    public String getSeverity() {
        return severity;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public List<String> getSuggestedFixes() {
        return suggestedFixes;
    }

    public List<String> getPreventionTips() {
        return preventionTips;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setHealthScore(int healthScore) {
        this.healthScore = healthScore;
    }

    public void setSuggestedFixes(List<String> suggestedFixes) {
        this.suggestedFixes = suggestedFixes;
    }

    public void setPreventionTips(List<String> preventionTips) {
        this.preventionTips = preventionTips;
    }
}