package com.demo.projects.server.task.service.api.controller.exception;

import com.demo.projects.server.task.service.api.exception.ValidationException;
import com.demo.projects.server.task.service.api.model.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> internalServerErrorHandler(Exception ex) {
        logger.debug("internalServerError::" + ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<String> badRequestHandler(ValidationException ex) {
        logger.debug("badRequest::" + ex);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
    }

}
