package com.demo.projects.server.task.service.api.postgresql.controller;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.postgresql.mapper.AccountMapper;
import com.demo.projects.server.task.service.api.postgresql.service.AccountPostgresqlService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/postgresql")
public class AccountController {

    private final AccountPostgresqlService accountPostgresqlService;

    private final AccountMapper accountMapper;

    public AccountController(AccountPostgresqlService accountPostgresqlService, AccountMapper accountMapper) {
        this.accountPostgresqlService = accountPostgresqlService;
        this.accountMapper = accountMapper;
    }

    @GetMapping(path = "/account/{id}")
    public Mono<UserDto> get(@PathVariable(name = "id") Long id) {
        return accountPostgresqlService.getById(id)
                .map(accountMapper::convert);
    }

    @GetMapping(path = "/accounts")
    public Flux<UserDto> getAll() {
        return accountPostgresqlService.getAll()
                .map(accountMapper::convert);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/account")
    public Mono<UserDto> create(@RequestBody UserDto user) {
        user.setId(null);
        return accountPostgresqlService.save(accountMapper.convert(user))
                .map(accountMapper::convert);
    }

    @PutMapping(path = "/account/{id}")
    public Mono<UserDto> update(@PathVariable(name = "id") Long id, @RequestBody UserDto user) {
        user.setId(id);
        return accountPostgresqlService.save(accountMapper.convert(user))
                .map(accountMapper::convert);
    }

    @DeleteMapping(path = "/account/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") Long id) {
        return accountPostgresqlService.delete(id);
    }
}
