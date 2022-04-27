package com.demo.projects.server.task.service.api.postgresql.client;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserServiceClient extends Client implements UserService {

    private final WebClient webClient;

    public UserServiceClient(@Value("${admin.service.base.url}") String adminServiceBaseUrl) {
        this.webClient = build(adminServiceBaseUrl);
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
