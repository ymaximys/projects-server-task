package com.demo.projects.server.task.service.api.controller;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "/project/{id}")
    public Mono<ProjectDto> getById(@PathVariable(name = "id") Long id) {
        logger.debug("Public api called to get project by id {}", id);
        return projectService.getById(id);
    }

    @GetMapping(path = "/projects")
    public Flux<ProjectDto> getAll() {
        return projectService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/project")
    public Mono<ProjectDto> create(@RequestBody ProjectDto user) {
        return projectService.create(user);
    }

    @PutMapping(path = "/project/{id}")
    public Mono<ProjectDto> update(@PathVariable(name = "id") Long id, @RequestBody ProjectDto project) {
        project.setId(id);
        return projectService.update(id, project);
    }

    @DeleteMapping(path = "/project/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") Long id) {
        return projectService.delete(id);
    }

    @PutMapping(path = "/project/{projectId}/user/{userId}")
    public Mono<Void> addAccount(@PathVariable(name = "projectId") Long projectId,
                                 @PathVariable(name = "userId") Long userId) {

        return projectService.assignAccount(projectId, userId);
    }

    @DeleteMapping(path = "/project/{projectId}/user/{userId}")
    public Mono<Void> removeAccount(@PathVariable(name = "projectId") Long projectId,
                                    @PathVariable(name = "userId") Long userId) {

        return projectService.unassignAccount(projectId, userId);
    }
}
