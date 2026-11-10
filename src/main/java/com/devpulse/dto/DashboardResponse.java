package com.devpulse.dto;

import java.time.LocalDateTime;

public class DashboardResponse {

    private long totalLogs;
    private long errorLogs;
    private long warnLogs;
    private long infoLogs;
    private long debugLogs;

    private double errorPercentage;

    private String healthStatus;

    private String mostActiveService;

    private LocalDateTime latestLogTime;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalLogs,
                             long errorLogs,
                             long warnLogs,
                             long infoLogs,
                             long debugLogs,
                             double errorPercentage,
                             String healthStatus,
                             String mostActiveService,
                             LocalDateTime latestLogTime) {

        this.totalLogs = totalLogs;
        this.errorLogs = errorLogs;
        this.warnLogs = warnLogs;
        this.infoLogs = infoLogs;
        this.debugLogs = debugLogs;
        this.errorPercentage = errorPercentage;
        this.healthStatus = healthStatus;
        this.mostActiveService = mostActiveService;
        this.latestLogTime = latestLogTime;
    }

    public long getTotalLogs() {
        return totalLogs;
    }

    public long getErrorLogs() {
        return errorLogs;
    }

    public long getWarnLogs() {
        return warnLogs;
    }

    public long getInfoLogs() {
        return infoLogs;
    }

    public long getDebugLogs() {
        return debugLogs;
    }

    public double getErrorPercentage() {
        return errorPercentage;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getMostActiveService() {
        return mostActiveService;
    }

    public LocalDateTime getLatestLogTime() {
        return latestLogTime;
    }
}