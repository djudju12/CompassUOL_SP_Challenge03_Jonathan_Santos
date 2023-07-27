package com.br.compassuol.msproducts.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PayloadProducts {
    private List<ProductDto> products;
    private Map<Long, String> errors;
}
