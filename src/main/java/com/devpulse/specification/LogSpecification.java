package com.devpulse.specification;

import org.springframework.data.jpa.domain.Specification;

import com.devpulse.entity.LogEntry;
import com.devpulse.enums.LogLevel;

public class LogSpecification {

    private LogSpecification() {
    }

    public static Specification<LogEntry> hasProjectId(Long projectId) {

        return (root, query, cb) ->
                cb.equal(root.get("project").get("id"), projectId);
    }

    public static Specification<LogEntry> hasLevel(LogLevel level) {

        return (root, query, cb) ->

                level == null
                        ? cb.conjunction()
                        : cb.equal(root.get("level"), level);
    }

    public static Specification<LogEntry> hasService(String service) {

        return (root, query, cb) ->

                service == null || service.isBlank()
                        ? cb.conjunction()
                        : cb.like(
                                cb.lower(root.get("service")),
                                "%" + service.toLowerCase() + "%");
    }

}