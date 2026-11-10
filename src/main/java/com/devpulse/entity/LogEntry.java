package com.devpulse.entity;

import java.time.LocalDateTime;

import com.devpulse.enums.LogLevel;

import jakarta.persistence.*;

@Entity
@Table(name = "logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogLevel level;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(columnDefinition = "TEXT")
    private String stackTrace;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public LogEntry() {
    }

    public LogEntry(Long id,
                    LogLevel level,
                    String service,
                    String message,
                    String stackTrace,
                    LocalDateTime timestamp,
                    Project project) {

        this.id = id;
        this.level = level;
        this.service = service;
        this.message = message;
        this.stackTrace = stackTrace;
        this.timestamp = timestamp;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}