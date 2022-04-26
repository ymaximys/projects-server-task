package com.demo.projects.server.task.service.api.postgresql.mapper;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.postgresql.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDto convert(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setStatus(project.getStatus());
        return projectDto;
    }

    public Project convert(ProjectDto projectDto) {
        Project project = new Project();
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setStatus(projectDto.getStatus());
        return project;
    }
}
