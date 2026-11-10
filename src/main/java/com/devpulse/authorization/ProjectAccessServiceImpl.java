package com.devpulse.authorization;

import org.springframework.stereotype.Service;

import com.devpulse.common.SecurityUtils;
import com.devpulse.entity.Project;
import com.devpulse.entity.User;
import com.devpulse.exception.ForbiddenOperationException;
import com.devpulse.exception.ResourceNotFoundException;
import com.devpulse.repository.ProjectRepository;
import com.devpulse.repository.UserRepository;

@Service
public class ProjectAccessServiceImpl implements ProjectAccessService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectAccessServiceImpl(
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project getAuthorizedProject(Long projectId) {

        String email = SecurityUtils.getCurrentUserEmail();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));

        if (!project.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenOperationException(
                    "You are not authorized to access this project.");
        }

        return project;
    }
}