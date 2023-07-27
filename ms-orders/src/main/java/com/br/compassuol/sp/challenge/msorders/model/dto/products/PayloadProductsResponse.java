package com.br.compassuol.sp.challenge.msorders.model.dto.products;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class PayloadProductsResponse {
    private List<ProductDto> products;
    private Map<Long, String> errors;
}
