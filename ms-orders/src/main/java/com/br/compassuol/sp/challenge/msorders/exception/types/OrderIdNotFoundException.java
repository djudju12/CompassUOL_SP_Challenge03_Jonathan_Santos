package com.br.compassuol.sp.challenge.msorders.exception.types;


public class OrderIdNotFoundException extends RuntimeException {
    public OrderIdNotFoundException(long id) {
        super(String.format("Order with id %d not found", id));
    }
}
