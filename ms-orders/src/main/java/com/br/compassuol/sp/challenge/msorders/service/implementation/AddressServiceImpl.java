package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.feign.AddressProxy;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import com.br.compassuol.sp.challenge.msorders.repository.AddressRepository;
import com.br.compassuol.sp.challenge.msorders.service.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressProxy addressProxy;
    private final AddressRepository addressRepository;
    private final DeliveryAddressMapper addressMapper;

    public AddressServiceImpl(AddressProxy addressProxy,
                              AddressRepository addressRepository,
                              DeliveryAddressMapper addressMapper) {
        this.addressProxy = addressProxy;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    // TODO - sera que da pra validar os dois campos com uma unica anotacao?
    public DeliveryAddressDto completeThisAddress(@Valid DeliveryAddressDto deliveryAddressDto) {
        String zipCode = deliveryAddressDto.getZipCode();
        String number = deliveryAddressDto.getNumber();
        if (!StringUtils.hasText(zipCode)) {
            throw new IllegalArgumentException("Zip code is required");
        }

        log.info("Completing address with zipcode: {}, number: {}", zipCode, number);

        DeliveryAddressDto completedAddress;
        if (addressRepository.existsByZipCodeAndNumber(zipCode, number)) {
            log.info("Address already exists in database. zipcode: {}, number: {}. Retrieving it...",
                    zipCode, number);

            DeliveryAddress savedAddress = addressRepository
                    .findByZipCodeAndNumber(zipCode, number);

            completedAddress = addressMapper.toDto(savedAddress);
            completedAddress.setNumber(number);

        } else {
            log.info("Querying address from external API {}", deliveryAddressDto);
            AddressResponse addressResponse = addressProxy.getAddressByCep(zipCode);
            // Remove o hifen do CEP
            addressResponse.setZipCode(cleanZipCode(addressResponse.getZipCode()));

            completedAddress = new DeliveryAddressDto();
            completedAddress.setNumber(number);
            BeanUtils.copyProperties(addressResponse, completedAddress);

        }

        return completedAddress;
    }

    private String cleanZipCode(String zipCode) {
        return zipCode.replaceAll("-", "");
    }
}
