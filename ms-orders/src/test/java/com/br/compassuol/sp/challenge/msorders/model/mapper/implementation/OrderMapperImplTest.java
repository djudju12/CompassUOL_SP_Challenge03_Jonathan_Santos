package com.br.compassuol.sp.challenge.msorders.model.mapper.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderMapperImplTest {

    @Mock
    private DeliveryAddressMapper addressMapper;

    @InjectMocks
    private OrderMapperImpl orderMapper;

    private final long ID = 1L;
    private final OrderStatus STATUS = OrderStatus.SHIPPED;
    private DeliveryAddressDto deliveryAddressDto;
    private DeliveryAddress deliveryAddress;
    private Order entity;
    private OrderDto dto;
    @BeforeEach
    void setUp() {
        deliveryAddress = new DeliveryAddress();
        deliveryAddressDto = new DeliveryAddressDto();
        List<OrderedProduct> products = new ArrayList<>();
        OrderedProduct product = new OrderedProduct();

        product.setId(ID);
        products.add(product);
        entity = new Order()
                .setId(ID)
                .setUserId(ID)
                .setStatus(STATUS)
                .setProducts(products)
                .setDeliveryAddress(deliveryAddress);

        dto = new OrderDto()
                .setId(ID)
                .setUserId(ID)
                .setStatus(STATUS)
                .setProducts(new PayloadProductsRequest().setIds(Lists.newArrayList(ID)))
                .setDeliveryAddress(deliveryAddressDto);
    }

    @Test
    void order_ToDto_OrderDto() {
        // given
        given(addressMapper.toDto(any(DeliveryAddress.class)))
                .willReturn(deliveryAddressDto);

        // when
        OrderDto dto = orderMapper.toDto(entity);

        // then
        then(addressMapper).should().toDto(entity.getDeliveryAddress());
        assertAll(
                () -> assertThat(dto.getId()).isEqualTo(ID),
                () -> assertThat(dto.getUserId()).isEqualTo(ID),
                () -> assertThat(dto.getStatus()).isEqualTo(STATUS),
                () -> assertThat(dto.getProducts()).isNotNull(),
                () -> assertThat(dto.getDeliveryAddress()).isNotNull()
        );
    }

    @Test
    void order_ToDto_DetailedOrderDto() {
        // when
        List<ProductDto> products = Lists.newArrayList(new ProductDto());
        DetailedOrderDto dto = orderMapper.toDto(entity, products);

        // then
        then(addressMapper).should().toDto(entity.getDeliveryAddress());
        assertAll(
                () -> assertThat(dto.getId()).isEqualTo(ID),
                () -> assertThat(dto.getUserId()).isEqualTo(ID),
                () -> assertThat(dto.getStatus()).isEqualTo(STATUS),
                () -> assertThat(dto.getProducts()).isEqualTo(products)
        );
    }

    @Test
    void orderDto_ToEntity() {
        // given
        given(addressMapper.toEntity(any(DeliveryAddressDto.class)))
                .willReturn(deliveryAddress);


        // when
        Order entity = orderMapper.toEntity(dto);

        // then
        then(addressMapper).should().toEntity(dto.getDeliveryAddress());
        assertAll(
                () -> assertThat(entity.getId()).isEqualTo(ID),
                () -> assertThat(entity.getUserId()).isEqualTo(ID),
                () -> assertThat(entity.getStatus()).isEqualTo(STATUS),
                () -> assertThat(entity.getProducts()).isNotNull(),
                () -> assertThat(entity.getDeliveryAddress()).isNotNull()
        );
    }

}