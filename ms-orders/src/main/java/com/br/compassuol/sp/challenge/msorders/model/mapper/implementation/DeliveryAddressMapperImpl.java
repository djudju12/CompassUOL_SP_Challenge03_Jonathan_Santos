package com.br.compassuol.sp.challenge.msorders.model.mapper.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import org.springframework.stereotype.Component;

@Component
public class DeliveryAddressMapperImpl implements DeliveryAddressMapper {
    @Override
    public DeliveryAddress toEntity(DeliveryAddressDto dto) {
        return new DeliveryAddress()
                .setId(dto.getId())
                .setCity(dto.getCity())
                .setDistrict(dto.getDistrict())
                .setNumber(dto.getNumber())
                .setStreet(dto.getStreet())
                .setZipCode(dto.getZipCode())
                .setDetailedAddress(dto.getDetailedAddress())
                .setState(dto.getState())
                .setDistrict(dto.getDistrict());
    }

    @Override
    public DeliveryAddressDto toDto(DeliveryAddress deliveryAddress) {
        return DeliveryAddressDto.builder()
                .id(deliveryAddress.getId())
                .number(deliveryAddress.getNumber())
                .street(deliveryAddress.getStreet())
                .city(deliveryAddress.getCity())
                .state(deliveryAddress.getState())
                .detailedAddress(deliveryAddress.getDetailedAddress())
                .zipCode(deliveryAddress.getZipCode())
                .district(deliveryAddress.getDistrict())
                .build();

    }
}
