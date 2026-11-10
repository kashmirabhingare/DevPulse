package com.devpulse.authorization;

import com.devpulse.entity.Project;

public interface ProjectAccessService {

    Project getAuthorizedProject(Long projectId);

}