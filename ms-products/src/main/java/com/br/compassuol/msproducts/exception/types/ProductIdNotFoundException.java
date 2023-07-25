package com.br.compassuol.msproducts.exception.types;


public class ProductIdNotFoundException extends RuntimeException {
    public ProductIdNotFoundException(long id) {
        super(String.format("Product with id %d not found", id));
    }
}
