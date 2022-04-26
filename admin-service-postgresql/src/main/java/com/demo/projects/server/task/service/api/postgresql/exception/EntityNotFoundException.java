package com.demo.projects.server.task.service.api.postgresql.exception;

import com.demo.projects.server.task.service.api.exception.ValidationException;

public class EntityNotFoundException extends ValidationException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
