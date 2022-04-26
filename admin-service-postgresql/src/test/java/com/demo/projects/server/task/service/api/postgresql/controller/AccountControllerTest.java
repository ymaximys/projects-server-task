package com.demo.projects.server.task.service.api.postgresql.controller;

import com.demo.projects.server.task.service.api.model.ProjectDto;
import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.postgresql.entity.Account;
import com.demo.projects.server.task.service.api.postgresql.entity.Project;
import com.demo.projects.server.task.service.api.postgresql.mapper.AccountMapper;
import com.demo.projects.server.task.service.api.postgresql.mapper.ProjectMapper;
import com.demo.projects.server.task.service.api.postgresql.repository.AccountRepository;
import com.demo.projects.server.task.service.api.postgresql.repository.ProjectRepository;
import com.demo.projects.server.task.service.api.postgresql.service.AccountPostgresqlService;
import com.demo.projects.server.task.service.api.postgresql.service.ProjectPostgresqlService;
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
@WebFluxTest(controllers = AccountController.class)
@Import({AccountPostgresqlService.class, AccountMapper.class})
public class AccountControllerTest {

    @MockBean
    private AccountRepository repository;

    @Autowired
    private WebTestClient webClient;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);
        account.setName("Name");
        account.setLogin("Login");
        account.setPassword("Password");
    }

    @Test
    public void testGetById() {
        Mockito.when(repository.findById(account.getId())).thenReturn(Mono.just(account));

        webClient.get().uri("/postgresql/account/{id}", account.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(account.getId())
                .jsonPath("$.login").isEqualTo(account.getLogin())
                .jsonPath("$.password").isEqualTo(account.getPassword());

        Mockito.verify(repository, times(1)).findById(account.getId());
    }

    @Test
    public void testGetAll() {
        Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(account)));

        webClient.get().uri("/postgresql/accounts")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        Mockito.when(repository.save(account)).thenReturn(Mono.just(account));

        UserDto body = new UserDto();
        body.setId(account.getId());
        body.setName(account.getName());
        body.setLogin(account.getLogin());
        body.setPassword(account.getPassword());

        webClient.post()
                .uri("/postgresql/account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(account);
    }

    @Test
    public void testUpdate() {
        Mockito.when(repository.save(account)).thenReturn(Mono.just(account));

        UserDto body = new UserDto();
        body.setId(account.getId());
        body.setName(account.getName());
        body.setLogin(account.getLogin());
        body.setPassword(account.getPassword());

        webClient.put()
                .uri("/postgresql/account/{id}", account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, times(1)).save(account);
    }

    @Test
    public void testDelete() {
        Mockito.when(repository.deleteById(account.getId())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/postgresql/account/{id}", account.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, times(1)).deleteById(account.getId());
    }
}
