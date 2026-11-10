package com.devpulse.service;

import java.util.List;

import com.devpulse.dto.CreateProjectRequest;
import com.devpulse.dto.ProjectResponse;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);

    List<ProjectResponse> getMyProjects();

}