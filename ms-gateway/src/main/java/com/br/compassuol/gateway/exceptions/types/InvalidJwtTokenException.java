package com.br.compassuol.gateway.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidJwtTokenException extends ResponseStatusException {
    public InvalidJwtTokenException() {
        super(HttpStatus.FORBIDDEN, "Invalid JWT Token");
    }
}
