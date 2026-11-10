package com.devpulse.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;
    private String name;
    private String apiKey;
    private LocalDateTime createdAt;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id,
                           String name,
                           String apiKey,
                           LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.apiKey = apiKey;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}