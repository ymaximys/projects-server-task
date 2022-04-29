package com.demo.projects.server.task.service.api.postgresql.controller;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.postgresql.entity.Account;
import com.demo.projects.server.task.service.api.postgresql.entity.Project;
import com.demo.projects.server.task.service.api.postgresql.entity.ProjectAccount;
import com.demo.projects.server.task.service.api.postgresql.mapper.ProjectMapper;
import com.demo.projects.server.task.service.api.postgresql.repository.AccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectAccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectRepository;
import com.demo.projects.server.task.service.api.postgresql.service.ProjectPostgresqlService;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProjectController.class)
@Import({ProjectPostgresqlService.class, ProjectMapper.class})
public class ProjectControllerTest {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ProjectAccountRepository projectAccountRepository;

    @Autowired
    private WebTestClient webClient;

    private Project project;
    private Account account;

    @BeforeEach
    public void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Name");
        project.setStatus(true);

        account = new Account();
        account.setId(10L);
    }

    @Test
    public void testGetById() {
        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Mono.just(project));

        webClient.get().uri("/postgresql/project/{id}", project.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(project.getId())
                .jsonPath("$.name").isEqualTo(project.getName())
                .jsonPath("$.status").isEqualTo(project.getStatus());

        Mockito.verify(projectRepository, times(1)).findById(project.getId());
    }

    @Test
    public void testGetAll() {
        Mockito.when(projectRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(project)));

        webClient.get().uri("/postgresql/projects")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProjectDto.class);

        Mockito.verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        project.setId(null);
        Mockito.when(projectRepository.save(eq(project))).thenReturn(Mono.just(project));

        ProjectDto body = new ProjectDto();
        body.setId(project.getId());
        body.setName(project.getName());
        body.setStatus(project.getStatus());

        webClient.post()
                .uri("/postgresql/project")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isCreated();

        project.setId(null);
        Mockito.verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testUpdate() {
        Mockito.when(projectRepository.save(project)).thenReturn(Mono.just(project));

        ProjectDto body = new ProjectDto();
        body.setId(project.getId());
        body.setName(project.getName());
        body.setStatus(project.getStatus());

        webClient.put()
                .uri("/postgresql/project/{id}", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testDelete() {
        Mockito.when(projectRepository.deleteById(project.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/postgresql/project/{id}", project.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectRepository, times(1)).deleteById(project.getId());
    }

    @Test
    public void testAssignAccount() {
        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Mono.just(project));
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Mono.just(account));

        Mockito.when(projectAccountRepository.findByProjectIdAndAccountId(project.getId(), account.getId()))
                .thenReturn(Mono.empty());

        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setProjectId(project.getId());
        projectAccount.setAccountId(account.getId());
        Mockito.when(projectAccountRepository.save(projectAccount)).thenReturn(Mono.just(projectAccount));

        webClient.put()
                .uri("/postgresql/project/{projectId}/account/{accountId}", project.getId(), account.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectRepository, times(1)).findById(project.getId());
        Mockito.verify(accountRepository, times(1)).findById(account.getId());
        Mockito.verify(projectAccountRepository, times(1))
                .findByProjectIdAndAccountId(project.getId(), account.getId());

        Mockito.verify(projectAccountRepository, times(1))
                .save(projectAccount);
    }

    @Test
    public void testUnassignAccount() {
        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Mono.just(project));
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Mono.just(account));

        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setId(333L);
        projectAccount.setProjectId(project.getId());
        projectAccount.setAccountId(account.getId());
        Mockito.when(projectAccountRepository.findByProjectIdAndAccountId(project.getId(), account.getId()))
                .thenReturn(Mono.just(projectAccount));

        Mockito.when(projectAccountRepository.deleteById(projectAccount.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/postgresql/project/{projectId}/account/{accountId}", project.getId(), account.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(projectRepository, times(1)).findById(project.getId());
        Mockito.verify(accountRepository, times(1)).findById(account.getId());
        Mockito.verify(projectAccountRepository, times(1))
                .findByProjectIdAndAccountId(project.getId(), account.getId());

        Mockito.verify(projectAccountRepository, times(1))
                .deleteById(projectAccount.getId());
    }


}
