package com.demo.projects.server.task.service.api.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService<T> {

    Mono<T> getById(Long id);

    Flux<T> getAll();

    Mono<T> create(T user);

    Mono<T> update(Long id, T user);

    Mono<Void> delete(Long id);
}
