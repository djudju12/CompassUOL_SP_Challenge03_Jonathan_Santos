package com.br.compassuol.sp.challenge.msorders.model.mapper;

import com.br.compassuol.sp.challenge.msorders.model.dto.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapperImpl implements OrderMapper{

    @Override
    public OrderDto toDto(Order order) {
        return new OrderDto()
                .setId(order.getId())
                .setUserId(order.getUserId())
                .setStatus(order.getStatus())
                .setProducts( orderedProductListToProductListDto(order.getProducts()) );
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
        return new Order()
                .setId(orderDto.getId())
                .setStatus(orderDto.getStatus())
                .setProducts( productListToOrderedProduct(orderDto.getProducts()) )
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
