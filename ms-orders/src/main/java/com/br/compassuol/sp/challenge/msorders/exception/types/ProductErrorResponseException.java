package com.br.compassuol.sp.challenge.msorders.exception.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductErrorResponseException extends RuntimeException {
    private Map<Long, String> errors;
    public ProductErrorResponseException(Map<Long, String> errors) {
        super("Error on product service");
        this.errors = errors;
    }
}
