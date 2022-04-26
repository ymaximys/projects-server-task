package com.demo.projects.server.task.service.api.postgresql.client;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserServiceClient implements UserService {

    private static final String DEFAULT_BASE_URL = "http://localhost:8082/postgresql"; //TODO: provide through property file

    private final WebClient webClient;

    public UserServiceClient() {
        this.webClient = WebClient.create(DEFAULT_BASE_URL);
    }


    @Override
    public Mono<UserDto> getById(Long id) {
        return webClient.get().uri("/account/" + id)
                .exchangeToMono(response -> response.bodyToMono(UserDto.class));
    }

    @Override
    public Flux<UserDto> getAll() {
        return webClient.get().uri("/accounts")
                .exchangeToFlux(response -> response.bodyToFlux(UserDto.class));
    }

    @Override
    public Mono<UserDto> create(UserDto user) {
        return webClient.post().uri("/account")
                .bodyValue(user)
                .exchangeToMono(response -> response.bodyToMono(UserDto.class));
    }

    @Override
    public Mono<UserDto> update(Long id, UserDto user) {
        return webClient.put().uri("/account/" + id)
                .bodyValue(user)
                .exchangeToMono(response -> response.bodyToMono(UserDto.class));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return webClient.delete().uri("/account/" + id)
                .exchangeToMono(response -> response.bodyToMono(Void.class));
    }
}
