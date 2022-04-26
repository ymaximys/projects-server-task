package com.demo.projects.server.task.service.api.controller;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProjectService projectService;

    private ProjectDto project;

    private Long userId = 100L;

    @BeforeEach
    public void setUp() {
        project = new ProjectDto();
        project.setId(1L);
        project.setName("Name");
        project.setStatus(true);
    }

    @Test
    public void testGetById() {
        Mockito.when(projectService.getById(project.getId())).thenReturn(Mono.just(project));

        webClient.get().uri("/project/{id}", project.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(project.getId())
                .jsonPath("$.name").isEqualTo(project.getName())
                .jsonPath("$.status").isEqualTo(project.getStatus());

        Mockito.verify(projectService, times(1)).getById(project.getId());
    }

    @Test
    public void testGetAll() {
        Mockito.when(projectService.getAll()).thenReturn(Flux.fromIterable(Arrays.asList(project)));

        webClient.get().uri("/projects")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProjectDto.class);

        Mockito.verify(projectService, times(1)).getAll();
    }

    @Test
    public void testCreate() {
        Mockito.when(projectService.create(project)).thenReturn(Mono.just(project));

        webClient.post()
                .uri("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(project))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(projectService, times(1)).create(project);
    }

    @Test
    public void testUpdate() {
        Mockito.when(projectService.update(project.getId(), project)).thenReturn(Mono.just(project));

        webClient.put()
                .uri("/project/{id}", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(project))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectService, times(1)).update(project.getId(), project);
    }

    @Test
    public void testDelete() {
        Mockito.when(projectService.delete(project.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/project/{id}", project.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectService, times(1)).delete(project.getId());
    }

    @Test
    public void testAssignAccount() {
        Mockito.when(projectService.assignAccount(project.getId(), userId)).thenReturn(Mono.empty());

        webClient.put()
                .uri("/project/{projectId}/user/{userId}", project.getId(), userId)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectService, times(1))
                .assignAccount(project.getId(), userId);
    }

    @Test
    public void testUnassignAccount() {
        Mockito.when(projectService.unassignAccount(project.getId(), userId)).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/project/{projectId}/user/{userId}", project.getId(), userId)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectService, times(1))
                .unassignAccount(project.getId(), userId);
    }

}
