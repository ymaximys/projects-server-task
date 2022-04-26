package com.demo.projects.server.task.service.api.service;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import reactor.core.publisher.Mono;

public interface ProjectService extends CrudService<ProjectDto> {

    Mono<Void> assignAccount(Long projectId, Long userId);

    Mono<Void> unassignAccount(Long projectId, Long usertId);

}
