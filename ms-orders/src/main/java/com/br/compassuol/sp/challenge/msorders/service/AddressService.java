package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AddressService {
    DeliveryAddressDto completeThisAddress(@Valid DeliveryAddressDto deliveryAddressDto);
}
