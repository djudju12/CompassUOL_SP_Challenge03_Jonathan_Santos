package com.br.compassuol.msproducts.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PayloadProducts {
    private List<ProductDto> products;
}