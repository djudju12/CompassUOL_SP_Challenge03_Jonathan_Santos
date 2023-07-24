package com.br.compassuol.gateway.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnathorizedJwtTokenException extends ResponseStatusException {
    public UnathorizedJwtTokenException(Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, cause.getMessage());
    }
}
