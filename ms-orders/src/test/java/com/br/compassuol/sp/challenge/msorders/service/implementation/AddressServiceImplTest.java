package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.feign.AddressProxy;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import com.br.compassuol.sp.challenge.msorders.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressProxy addressProxy;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private DeliveryAddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private final String ZIP_CODE = "12345678";
    private final String NUMBER = "123";
    private DeliveryAddressDto DELIVERY_ADDRESS_DTO;

    @BeforeEach
    void setUp() {
        DELIVERY_ADDRESS_DTO = DeliveryAddressDto.builder()
                .zipCode(ZIP_CODE)
                .number(NUMBER)
                .build();
    }

    @Test
    void completeThisAddress_InputAlreadyExists_QueryDb() {
        // Given
        DeliveryAddress expectedAddress = mock(DeliveryAddress.class);


        given(addressRepository.existsByZipCodeAndNumber(ZIP_CODE, NUMBER))
                .willReturn(true);

        given(addressRepository.findByZipCodeAndNumber(ZIP_CODE, NUMBER))
                .willReturn(expectedAddress);

        given(addressMapper.toDto(expectedAddress))
                .willReturn(new DeliveryAddressDto());

        // When
        DeliveryAddressDto actualAddress = addressService.completeThisAddress(DELIVERY_ADDRESS_DTO);

        // Then
        assertThat(actualAddress.getNumber()).isEqualTo(NUMBER);
        then(addressRepository).should().existsByZipCodeAndNumber(ZIP_CODE, NUMBER);
        then(addressRepository).should().findByZipCodeAndNumber(ZIP_CODE, NUMBER);
        then(addressMapper).should().toDto(expectedAddress);
    }

    @Test
    void completeThisAddress_InputAlreadyExists_QueryApi() {
        // Given
        AddressResponse response = AddressResponse.builder()
                .city("Santa Cruz do Sul")
                .state("RS")
                .district("")
                .zipCode(ZIP_CODE)
                .detailedAddress("")
                .build();

        DeliveryAddressDto dto = new DeliveryAddressDto();
        dto.setZipCode(ZIP_CODE);
        dto.setNumber(NUMBER);

        DeliveryAddress expectedAddress = mock(DeliveryAddress.class);

        given(addressRepository.existsByZipCodeAndNumber(ZIP_CODE, NUMBER))
                .willReturn(false);
        given(addressProxy.getAddressByCep(ZIP_CODE))
                .willReturn(response);

        // When
        DeliveryAddressDto actualAddress = addressService.completeThisAddress(dto);

        // Then
        assertThat(actualAddress.getZipCode()).isEqualTo(dto.getZipCode());
        then(addressProxy).should().getAddressByCep(ZIP_CODE);
        assertAll(
                () -> assertThat(actualAddress.getCity()).isEqualTo(response.getCity()),
                () -> assertThat(actualAddress.getState()).isEqualTo(response.getState()),
                () -> assertThat(actualAddress.getDistrict()).isEqualTo(response.getDistrict()),
                () -> assertThat(actualAddress.getDetailedAddress()).isEqualTo(response.getDetailedAddress()),
                () -> assertThat(actualAddress.getZipCode()).isEqualTo(response.getZipCode()),
                () -> assertThat(actualAddress.getNumber()).isEqualTo(dto.getNumber())
        );
    }
}