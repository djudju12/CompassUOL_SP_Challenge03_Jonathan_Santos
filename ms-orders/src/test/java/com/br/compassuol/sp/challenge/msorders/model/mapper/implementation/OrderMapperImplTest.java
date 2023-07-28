package com.br.compassuol.sp.challenge.msorders.model.mapper.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.mapper.DeliveryAddressMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderMapperImplTest {

    @Mock
    private DeliveryAddressMapper addressMapper;

    @InjectMocks
    private OrderMapperImpl orderMapper;

    @Test
    void toDto() {
        // given
        Order order = new Order();
        order.setProducts(Lists.newArrayList(new OrderedProduct()));
        given(addressMapper.toDto(order.getDeliveryAddress()))
                .willReturn(new DeliveryAddressDto());

        // when
        OrderDto dto = orderMapper.toDto(order);

        // then
        assertNotNull(dto);
    }

    @Test
    void testToDto() {
    }

    @Test
    void toEntity() {
    }

    @Test
    void productListToOrderedProduct() {
    }

    @Test
    void toProductRequest() {
    }
}