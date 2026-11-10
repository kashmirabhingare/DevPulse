package com.devpulse.service;

import com.devpulse.dto.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(Long projectId);

}