package com.br.compassuol.sp.challenge.msorders.exception.types;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(Long userId) {
        super(String.format("User with id %d not found", userId));
    }
}
