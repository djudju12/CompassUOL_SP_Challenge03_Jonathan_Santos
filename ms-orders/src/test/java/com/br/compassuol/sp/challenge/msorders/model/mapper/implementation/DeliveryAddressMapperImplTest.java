package com.br.compassuol.sp.challenge.msorders.model.mapper.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressMapperImplTest {

    private DeliveryAddressMapperImpl mapper;

    private final long ID = 1L;
    private final String CITY = "São Paulo";
    private final String DISTRICT = "Vila Olímpia";
    private final String NUMBER = "100";
    private final String STREET = "Rua Fidêncio Ramos";
    private final String ZIP_CODE = "04551-010";
    private final String DETAILED_ADDRESS = "Complemento";
    private final String STATE = "SP";
    private DeliveryAddress entity;
    private DeliveryAddressDto dto;

    @BeforeEach
    void setUp() {
        mapper = new DeliveryAddressMapperImpl();
        entity = new DeliveryAddress()
                .setId(ID)
                .setCity(CITY)
                .setDistrict(DISTRICT)
                .setNumber(NUMBER)
                .setStreet(STREET)
                .setZipCode(ZIP_CODE)
                .setDetailedAddress(DETAILED_ADDRESS)
                .setState(STATE)
                .setDistrict(DISTRICT);

        dto = DeliveryAddressDto.builder()
                .id(ID)
                .number(NUMBER)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .detailedAddress(DETAILED_ADDRESS)
                .zipCode(ZIP_CODE)
                .district(DISTRICT)
                .build();

    }

    @Test
    void toEntity() {
        // when
        DeliveryAddress mappedEntity = mapper.toEntity(dto);

        // then
        assertAll(
                () -> assertThat(mappedEntity.getId()).isEqualTo(ID),
                () -> assertThat(mappedEntity.getCity()).isEqualTo(CITY),
                () -> assertThat(mappedEntity.getDistrict()).isEqualTo(DISTRICT),
                () -> assertThat(mappedEntity.getNumber()).isEqualTo(NUMBER),
                () -> assertThat(mappedEntity.getStreet()).isEqualTo(STREET),
                () -> assertThat(mappedEntity.getZipCode()).isEqualTo(ZIP_CODE),
                () -> assertThat(mappedEntity.getDetailedAddress()).isEqualTo(DETAILED_ADDRESS),
                () -> assertThat(mappedEntity.getState()).isEqualTo(STATE),
                () -> assertThat(mappedEntity.getDistrict()).isEqualTo(DISTRICT)
        );
    }

    @Test
    void toDto() {
        // when
        DeliveryAddressDto mappedDto = mapper.toDto(entity);

        // then
        assertAll(
                () -> assertThat(mappedDto.getId()).isEqualTo(ID),
                () -> assertThat(mappedDto.getCity()).isEqualTo(CITY),
                () -> assertThat(mappedDto.getDistrict()).isEqualTo(DISTRICT),
                () -> assertThat(mappedDto.getNumber()).isEqualTo(NUMBER),
                () -> assertThat(mappedDto.getStreet()).isEqualTo(STREET),
                () -> assertThat(mappedDto.getZipCode()).isEqualTo(ZIP_CODE),
                () -> assertThat(mappedDto.getDetailedAddress()).isEqualTo(DETAILED_ADDRESS),
                () -> assertThat(mappedDto.getState()).isEqualTo(STATE),
                () -> assertThat(mappedDto.getDistrict()).isEqualTo(DISTRICT)
        );
    }
}