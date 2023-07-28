package com.br.compassuol.sp.challenge.msorders.model.dto.orders;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DetailedOrderDto {
    @JsonIgnore
    private Long id;
    private Long userId;
    private OrderStatus status;
    private List<ProductDto> products;
    private DeliveryAddressDto deliveryAddress;
}
