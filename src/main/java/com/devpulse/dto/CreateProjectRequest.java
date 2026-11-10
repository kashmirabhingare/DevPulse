package com.devpulse.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    public CreateProjectRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}