package com.br.compassuol.sp.challenge.msorders.exception.types;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("Invalid address.");
    }
}
