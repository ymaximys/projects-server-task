package com.demo.projects.server.task.service.api.postgresql.exception;

public class EntityNotFoundException extends RuntimeException {

    private final String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
