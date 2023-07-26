package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;

public interface AddressService {
    DeliveryAddressDto completeThisAddress(DeliveryAddressDto deliveryAddressDto);
}
