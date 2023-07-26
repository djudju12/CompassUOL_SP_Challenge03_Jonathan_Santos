package com.br.compassuol.sp.challenge.msorders.model.dto.products;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO - payloadIn, payloadOut ... in other folder :)
@Data
@NoArgsConstructor
public class PayloadProducts {
    private List<ProductDto> products;
}
