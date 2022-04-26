package com.demo.projects.server.task.service.api.postgresql.repository;

import com.demo.projects.server.task.service.api.postgresql.entity.Account;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface AccountRepository extends ReactiveSortingRepository<Account, Long> {
}
