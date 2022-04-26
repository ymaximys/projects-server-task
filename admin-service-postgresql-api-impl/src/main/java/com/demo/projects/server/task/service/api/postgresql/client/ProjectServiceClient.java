package com.demo.projects.server.task.service.api.postgresql.client;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.service.ProjectService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProjectServiceClient implements ProjectService {

    private static final String DEFAULT_BASE_URL = "http://localhost:8082/postgresql"; //DOTO: provide through property file

    private final WebClient webClient;

    public ProjectServiceClient() {
        this.webClient = WebClient.create(DEFAULT_BASE_URL);
    }

    @Override
    public Mono<ProjectDto> getById(Long id) {
        return webClient.get().uri("/project/" + id)
                .exchangeToMono(response -> response.bodyToMono(ProjectDto.class));
    }

    @Override
    public Flux<ProjectDto> getAll() {
        return webClient.get().uri("/projects")
                .exchangeToFlux(response -> response.bodyToFlux(ProjectDto.class));
    }

    @Override
    public Mono<ProjectDto> create(ProjectDto user) {
        return webClient.post().uri("/project")
                .bodyValue(user)
                .exchangeToMono(response -> response.bodyToMono(ProjectDto.class));
    }

    @Override
    public Mono<ProjectDto> update(Long id, ProjectDto user) {
        return webClient.put().uri("/project/" + id)
                .bodyValue(user)
                .exchangeToMono(response -> response.bodyToMono(ProjectDto.class));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return webClient.delete().uri("/project/" + id)
                .exchangeToMono(response -> response.bodyToMono(Void.class));
    }

    @Override
    public Mono<Void> assignAccount(Long projectId, Long userId) {
        return webClient.put().uri("/project/" + projectId + "/account/" + userId)
                .exchangeToMono(response -> response.bodyToMono(Void.class));
    }

    @Override
    public Mono<Void> unassignAccount(Long projectId, Long usertId) {
        return webClient.delete().uri("/project/" + projectId + "/account/" + usertId)
                .exchangeToMono(response -> response.bodyToMono(Void.class));
    }
}
