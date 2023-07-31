package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AddressService {
    DeliveryAddressDto completeThisAddress(@Valid DeliveryAddressDto deliveryAddressDto);
    DeliveryAddress completeInvalidAddress(@Valid DeliveryAddressDto deliveryAddressDto);
}
