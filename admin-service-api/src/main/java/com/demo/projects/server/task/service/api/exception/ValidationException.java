package com.demo.projects.server.task.service.api.exception;

public class ValidationException extends RuntimeException {

    private final String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
