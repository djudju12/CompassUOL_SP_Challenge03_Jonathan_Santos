package com.br.compassuol.sp.challenge.msorders.model.dto;

import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDto {
    private Long id;
    private Long userId;
    private List<CartDto> products;
    private OrderStatus status;
}
