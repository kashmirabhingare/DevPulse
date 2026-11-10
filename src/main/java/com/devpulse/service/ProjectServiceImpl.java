package com.devpulse.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.devpulse.mapper.ProjectMapper;
import com.devpulse.common.SecurityUtils;
import com.devpulse.dto.CreateProjectRequest;
import com.devpulse.dto.ProjectResponse;
import com.devpulse.entity.Project;
import com.devpulse.entity.User;
import com.devpulse.exception.ResourceNotFoundException;
import com.devpulse.repository.ProjectRepository;
import com.devpulse.repository.UserRepository;
import com.devpulse.util.ApiKeyGenerator;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              UserRepository userRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String apiKey;

        do {
            apiKey = ApiKeyGenerator.generateApiKey();
        } while (projectRepository.existsByApiKey(apiKey));

        Project project = new Project();

        project.setName(request.getName());
        project.setApiKey(apiKey);
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);

        return ProjectMapper.toResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getMyProjects() {

        String email = SecurityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return projectRepository.findByUserId(user.getId())
                .stream()
                .map(ProjectMapper::toResponse)
                .collect(Collectors.toList());
    }
}