package com.finalhomework.pokemonservice;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public final class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final List<Map<String, String>> errors;

    public ErrorResponse(HttpStatus status, String message, List<Map<String, String>> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }
}
