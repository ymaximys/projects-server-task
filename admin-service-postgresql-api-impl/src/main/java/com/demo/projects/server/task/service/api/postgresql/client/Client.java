package com.demo.projects.server.task.service.api.postgresql.client;

import com.demo.projects.server.task.service.api.exception.ValidationException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class Client {

    protected WebClient build(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl).filter(errorHandler()).build();
    }

    protected static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new Exception(errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new ValidationException(errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
