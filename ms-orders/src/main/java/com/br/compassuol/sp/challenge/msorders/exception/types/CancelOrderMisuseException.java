package com.br.compassuol.sp.challenge.msorders.exception.types;

public class CancelOrderMisuseException extends RuntimeException {
    private static final String message = "Cancel order misuse. You can only cancel orders with the DELETE HTTP verb.";

    public CancelOrderMisuseException() {
        super(message);
    }
}
