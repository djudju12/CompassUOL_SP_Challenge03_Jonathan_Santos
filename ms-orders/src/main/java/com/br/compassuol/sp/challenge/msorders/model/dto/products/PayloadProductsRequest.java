package com.br.compassuol.sp.challenge.msorders.model.dto.products;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayloadProductsRequest {
    List<Long> ids;
}