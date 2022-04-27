package com.demo.projects.server.task.service.api.postgresql.controller;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.postgresql.mapper.ProjectMapper;
import com.demo.projects.server.task.service.api.postgresql.service.ProjectPostgresqlService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/postgresql")
public class ProjectController {

    private final ProjectPostgresqlService projectPostgresqlService;

    private final ProjectMapper projectMapper;

    public ProjectController(ProjectPostgresqlService projectPostgresqlService, ProjectMapper projectMapper) {
        this.projectPostgresqlService = projectPostgresqlService;
        this.projectMapper = projectMapper;
    }

    @GetMapping(path = "/project/{id}")
    public Mono<ProjectDto> get(@PathVariable(name = "id") Long id) {
        return projectPostgresqlService.getById(id)
                .map(projectMapper::convert);
    }

    @GetMapping(path = "/projects")
    public Flux<ProjectDto> getAll() {
        return projectPostgresqlService.getAll()
                .map(projectMapper::convert);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/project")
    public Mono<ProjectDto> create(@RequestBody ProjectDto project) {
        project.setId(null);
        return projectPostgresqlService.save(projectMapper.convert(project))
                .map(projectMapper::convert);
    }

    @PutMapping(path = "/project/{id}")
    public Mono<ProjectDto> update(@PathVariable(name = "id") Long id, @RequestBody ProjectDto project) {
        project.setId(id);
        return projectPostgresqlService.save(projectMapper.convert(project))
                .map(projectMapper::convert);
    }

    @DeleteMapping(path = "/project/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") Long id) {
        return projectPostgresqlService.delete(id);
    }

    @PutMapping(path = "/project/{projectId}/account/{accountId}")
    public Mono<Void> addAccount(@PathVariable(name = "projectId") Long projectId,
                                 @PathVariable(name = "accountId") Long accountId) {

        return projectPostgresqlService.assignAccount(projectId, accountId);
    }

    @DeleteMapping(path = "/project/{projectId}/account/{accountId}")
    public Mono<Void> removeAccount(@PathVariable(name = "projectId") Long projectId,
                                    @PathVariable(name = "accountId") Long accountId) {

        return projectPostgresqlService.unassignAccount(projectId, accountId);
    }

}
