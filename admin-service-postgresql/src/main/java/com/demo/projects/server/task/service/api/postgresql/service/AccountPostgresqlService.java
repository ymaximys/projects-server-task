package com.demo.projects.server.task.service.api.postgresql.service;

import com.demo.projects.server.task.service.api.postgresql.entity.Account;
import com.demo.projects.server.task.service.api.postgresql.repository.AccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountPostgresqlService {

    private final AccountRepository accountRepository;

    public AccountPostgresqlService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    public Mono<Void> delete(Long id) {
        return accountRepository.deleteById(id);
    }
}
