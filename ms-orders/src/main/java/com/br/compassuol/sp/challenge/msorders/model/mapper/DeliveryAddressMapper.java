package com.br.compassuol.sp.challenge.msorders.model.mapper;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;

public interface DeliveryAddressMapper {
    DeliveryAddressDto toDto(DeliveryAddress deliveryAddress);
    DeliveryAddress toEntity(DeliveryAddressDto dto);
}
