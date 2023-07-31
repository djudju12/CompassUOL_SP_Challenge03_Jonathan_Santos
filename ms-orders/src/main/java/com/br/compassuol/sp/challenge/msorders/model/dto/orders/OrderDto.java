package com.br.compassuol.sp.challenge.msorders.model.dto.orders;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDto {
    private Long id;

    @NotNull(message = "User id is required")
    @Positive(message = "Invalid user id")
    private Long userId;

    @NotNull(message = "Products is required")
    @Valid
    private PayloadProductsRequest products;

    private OrderStatus status;

    @NotNull(message = "Delivery address is required")
    private DeliveryAddressDto deliveryAddress;
}
