package com.max_orlov.music_albums_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<?> handleRequestException(HttpServerErrorException.InternalServerError ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
