package com.demo.projects.server.task.service.api.controller;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.service.UserService;
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

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
public class AccountControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setLogin("Login");
        userDto.setPassword("Password");
    }

    @Test
    public void testGetById() {
        Mockito.when(userService.getById(userDto.getId())).thenReturn(Mono.just(userDto));

        webClient.get().uri("/user/{id}", userDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(userDto.getId())
                .jsonPath("$.login").isEqualTo(userDto.getLogin())
                .jsonPath("$.password").isEqualTo(userDto.getPassword());

        Mockito.verify(userService, times(1)).getById(userDto.getId());
    }

    @Test
    public void testGetAll() {
        Mockito.when(userService.getAll()).thenReturn(Flux.fromIterable(Arrays.asList(userDto)));

        webClient.get().uri("/users")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);

        Mockito.verify(userService, times(1)).getAll();
    }

    @Test
    public void testCreate() {
        Mockito.when(userService.create(userDto)).thenReturn(Mono.just(userDto));

        webClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(userService, times(1)).create(userDto);
    }

    @Test
    public void testUpdate() {
        Mockito.when(userService.update(userDto.getId(), userDto)).thenReturn(Mono.just(userDto));

        webClient.put()
                .uri("/user/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(userService, times(1)).update(userDto.getId(), userDto);
    }

    @Test
    public void testDelete() {
        Mockito.when(userService.delete(userDto.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/user/{id}", userDto.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(userService, times(1)).delete(userDto.getId());
    }
}
