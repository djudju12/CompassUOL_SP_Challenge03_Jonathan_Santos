package com.br.compassuol.msproducts.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayloadProducts {
    private List<ProductDto> products;
    private Map<Long, String> errors;
}
