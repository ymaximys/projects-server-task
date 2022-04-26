package com.demo.projects.server.task.service.api.postgresql.repository;

import com.demo.projects.server.task.service.api.postgresql.entity.ProjectAccount;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface ProjectAccountRepository extends ReactiveSortingRepository<ProjectAccount, Long> {

    Mono<ProjectAccount> findByProjectIdAndAccountId(Long projectId, Long accountId);


}
