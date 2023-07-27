package com.br.compassuol.msproducts.exception.types;


public class ProductIdNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Product with id not found.";

    public ProductIdNotFoundException(long id) {
        super(MESSAGE + " Id: " + id);
    }
}
