package com.demo.projects.server.task.service.api.postgresql.repository;

import com.demo.projects.server.task.service.api.postgresql.entity.Project;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ProjectRepository extends ReactiveSortingRepository<Project, Long> {
}
