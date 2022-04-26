package com.demo.projects.server.task.service.api.postgresql.controller.exception;

import com.demo.projects.server.task.service.api.model.ErrorDto;
import com.demo.projects.server.task.service.api.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDto> internalServerErrorHandler(Exception ex) {
        logger.debug("internalServerError::" + ex);
        return ResponseEntity.internalServerError().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorDto> badRequestHandler(ValidationException ex) {
        logger.debug("badRequest::" + ex);
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

}
