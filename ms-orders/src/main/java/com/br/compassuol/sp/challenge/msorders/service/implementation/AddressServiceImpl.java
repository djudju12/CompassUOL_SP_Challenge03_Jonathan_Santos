package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.exception.types.AddressNotFoundException;
import com.br.compassuol.sp.challenge.msorders.feign.AddressProxy;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import com.br.compassuol.sp.challenge.msorders.repository.AddressRepository;
import com.br.compassuol.sp.challenge.msorders.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    /*
     * Take an address with zip code and number and complete it
     * with the other fields. If the address is already in the database,
     * retrieve it, else query the external API and save it in the database
     */
    @Override
    public DeliveryAddressDto completeThisAddress(DeliveryAddressDto deliveryAddressDto) {
        String zipCode = cleanZipCode(deliveryAddressDto.getZipCode());
        String number = deliveryAddressDto.getNumber();
        DeliveryAddressDto completedAddress;

        if (addressRepository.existsByZipCodeAndNumber(zipCode, number)) {
            log.info("Address already exists in database. zipcode: {}, number: {}. Retrieving it...",
                    zipCode, number);
            DeliveryAddress savedAddress = addressRepository.findByZipCodeAndNumber(zipCode, number);

            completedAddress = addressMapper.toDto(savedAddress);
            completedAddress.setNumber(number);

        } else {
            log.info("Querying address from external API {}", deliveryAddressDto);
            AddressResponse addressResponse = addressProxy.getAddressByCep(zipCode);

            if (addressResponse.getZipCode() == null) {
                throw new AddressNotFoundException();
            }
                                    // Remove hyphen from zip code
            addressResponse.setZipCode(cleanZipCode(addressResponse.getZipCode()));

            completedAddress = new DeliveryAddressDto();
            completedAddress.setNumber(number);

            // Copy other fields of the response to the DTOs
            BeanUtils.copyProperties(addressResponse, completedAddress);
        }

        return completedAddress;
    }

    private String cleanZipCode(String zipCode) {
        return zipCode.replaceAll("-", "");
    }

}
