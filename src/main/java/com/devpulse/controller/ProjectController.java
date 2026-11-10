package com.devpulse.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.devpulse.dto.CreateProjectRequest;
import com.devpulse.dto.ProjectResponse;
import com.devpulse.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        return projectService.createProject(request);
    }

    @GetMapping
    public List<ProjectResponse> getMyProjects() {

        return projectService.getMyProjects();
    }
}