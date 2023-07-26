package com.br.compassuol.sp.challenge.msorders.model.dto.products;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PayloadProductsResponse {
    private List<ProductDto> products;
}
