package com.br.compassuol.sp.challenge.msorders.model.dto;

import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DetailedOrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private List<ProductDto> products;
}
