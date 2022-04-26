package com.demo.projects.server.task.service.api.controller;

import com.demo.projects.server.task.service.api.model.UserDto;
import com.demo.projects.server.task.service.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/{id}")
    public Mono<UserDto> getById(@PathVariable(name = "id") Long id) {
        logger.debug("Public api called to get user by id {}", id);
        return userService.getById(id);
    }

    @GetMapping(path = "/users")
    public Flux<UserDto> getAll() {
        return userService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/user")
    public Mono<UserDto> create(@RequestBody UserDto user) {
        return userService.create(user);
    }

    @PutMapping(path = "/user/{id}")
    public Mono<UserDto> update(@PathVariable(name = "id") Long id, @RequestBody UserDto user) {
        user.setId(id);
        return userService.update(id, user);
    }

    @DeleteMapping(path = "/user/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") Long id) {
        return userService.delete(id);
    }


}
