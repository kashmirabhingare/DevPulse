package com.devpulse.mapper;

import com.devpulse.dto.ProjectResponse;
import com.devpulse.entity.Project;

public class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getApiKey(),
                project.getCreatedAt()
        );
    }

}