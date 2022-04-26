package com.demo.projects.server.task.service.api.postgresql.service;

import com.demo.projects.server.task.service.api.postgresql.entity.Project;
import com.demo.projects.server.task.service.api.postgresql.entity.ProjectAccount;
import com.demo.projects.server.task.service.api.postgresql.exception.EntityNotFoundException;
import com.demo.projects.server.task.service.api.postgresql.repository.AccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectAccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectPostgresqlService {

    private final Logger logger = LoggerFactory.getLogger(ProjectPostgresqlService.class);

    private final ProjectRepository projectRepository;

    private final AccountRepository accountRepository;

    private final ProjectAccountRepository projectAccountRepository;

    public ProjectPostgresqlService(ProjectRepository projectRepository,
                                    AccountRepository accountRepository,
                                    ProjectAccountRepository projectAccountRepository) {

        this.projectRepository = projectRepository;
        this.accountRepository = accountRepository;
        this.projectAccountRepository = projectAccountRepository;
    }

    public Mono<Project> getById(Long id) {
        return projectRepository.findById(id);
    }

    public Flux<Project> getAll() {
        return projectRepository.findAll();
    }

    public Mono<Project> save(Project project) {
        return projectRepository.save(project);
    }

    public Mono<Void> delete(Long id) {
        return projectRepository.deleteById(id);
    }

    public Mono<Void> assignAccount(Long projectId, Long accountId) {
        return projectRepository.findById(projectId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Project not found by " + projectId)))
                .flatMap(project -> accountRepository.findById(accountId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found by " + accountId)))
                        .flatMap(account -> projectAccountRepository.findByProjectIdAndAccountId(project.getId(), account.getId())
                                .switchIfEmpty(
                                        projectAccountRepository.save(createProjectAccount(projectId, accountId)))
                                .flatMap(projectAccount -> {
                                    logger.debug("User with id {} already assigned to project with id {}",
                                            accountId, projectId);
                                    return Mono.empty();
                                })
                        )
                );

    }

    public Mono<Void> unassignAccount(Long projectId, Long accountId) {
        return projectRepository.findById(projectId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Project not found by " + projectId)))
                .flatMap(project -> accountRepository.findById(accountId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found by " + accountId)))
                        .flatMap(account -> projectAccountRepository.findByProjectIdAndAccountId(project.getId(), account.getId())
                                .switchIfEmpty(
                                        Mono.defer(() -> {
                                            logger.debug("User with id {} not assigned to project with id {}, no need to remove",
                                                    accountId, projectId);
                                            return Mono.empty();
                                        })
                                ).flatMap(projectAccount -> projectAccountRepository.deleteById(projectAccount.getId()))
                        )
                );
    }


    private ProjectAccount createProjectAccount(Long projectId, Long accountId) {
        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setAccountId(accountId);
        projectAccount.setProjectId(projectId);
        return projectAccount;
    }
}
