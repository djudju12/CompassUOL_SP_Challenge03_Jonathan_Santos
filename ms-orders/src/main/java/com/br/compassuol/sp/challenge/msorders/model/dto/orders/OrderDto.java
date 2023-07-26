package com.br.compassuol.sp.challenge.msorders.model.dto.orders;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDto {
    private Long id;
    private Long userId;
    private PayloadProductsRequest products;
    private OrderStatus status;

    @NotNull(message = "Delivery address is required")
    private DeliveryAddressDto deliveryAddress;
}
