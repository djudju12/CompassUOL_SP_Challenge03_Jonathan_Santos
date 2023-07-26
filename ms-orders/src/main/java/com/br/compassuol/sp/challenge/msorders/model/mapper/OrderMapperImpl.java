package com.br.compassuol.sp.challenge.msorders.model.mapper;

import com.br.compassuol.sp.challenge.msorders.model.dto.address.DeliveryAddressDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.DeliveryAddress;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapperImpl implements OrderMapper{

    private final DeliveryAddressMapper addressMapper;

    public OrderMapperImpl(DeliveryAddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public OrderDto toDto(Order order) {
        DeliveryAddress deliveryAddress = order.getDeliveryAddress();
        return new OrderDto()
                .setId(order.getId())
                .setUserId(order.getUserId())
                .setStatus(order.getStatus())
                .setProducts( orderedProductListToProductListDto(order.getProducts()) )
                .setDeliveryAddress( addressMapper.toDto(deliveryAddress) );
        }

    @Override
    public DetailedOrderDto toDto(Order order, List<ProductDto> productDetails) {
        return new DetailedOrderDto()
                .setId(order.getId())
                .setUserId(order.getUserId())
                .setId(order.getId())
                .setStatus(order.getStatus())
                .setProducts(productDetails);
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        DeliveryAddressDto deliveryAddressDto = orderDto.getDeliveryAddress();
        return new Order()
                .setId(orderDto.getId())
                .setStatus(orderDto.getStatus())
                .setProducts( productListToOrderedProduct(orderDto.getProducts()) )
                .setDeliveryAddress( addressMapper.toEntity(deliveryAddressDto) )
                .setUserId(orderDto.getUserId());
    }

    @Override
    public List<OrderedProduct> productListToOrderedProduct(ProductListDto productsIds) {
        return productsIds
                .getIds()
                .stream()
                .map(OrderedProduct::new)
                .toList();
    }

    @Override
    public ProductListDto orderedProductListToProductListDto(List<OrderedProduct> orderedProducts) {
        return new ProductListDto().setIds(orderedProducts
                .stream()
                .map(OrderedProduct::getProductId)
                .toList());
    }

}
