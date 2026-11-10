package com.devpulse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpulse.entity.LogEntry;
import com.devpulse.enums.LogLevel;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends
        JpaRepository<LogEntry, Long>,
        JpaSpecificationExecutor<LogEntry> {

	Page<LogEntry> findByProjectIdOrderByTimestampDesc(
	        Long projectId,
	        Pageable pageable
	);
	
    List<LogEntry> findByProjectId(Long projectId);

    List<LogEntry> findTop100ByProjectIdOrderByTimestampDesc(Long projectId);
    
    List<LogEntry> findByProjectIdAndLevel(Long projectId, LogLevel level);

    List<LogEntry> findByProjectIdOrderByTimestampDesc(Long projectId);

    List<LogEntry> findByProjectIdAndTimestampBetween(
            Long projectId,
            LocalDateTime start,
            LocalDateTime end
    );
    long countByProjectId(Long projectId);

    long countByProjectIdAndLevel(Long projectId, LogLevel level);

    LogEntry findTopByProjectIdOrderByTimestampDesc(Long projectId);
    
    @Query("""
    	       SELECT l.service
    	       FROM LogEntry l
    	       WHERE l.project.id = :projectId
    	       GROUP BY l.service
    	       ORDER BY COUNT(l) DESC
    	       LIMIT 1
    	       """)
    	String findMostActiveService(Long projectId);
}