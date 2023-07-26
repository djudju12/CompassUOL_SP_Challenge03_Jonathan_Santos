package com.br.compassuol.sp.challenge.msorders.model.mapper;

import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.model.entity.Order;
import com.br.compassuol.sp.challenge.msorders.model.entity.OrderedProduct;

import java.util.List;

public interface OrderMapper {
    OrderDto toDto(Order order);
    DetailedOrderDto toDto(Order order, List<ProductDto> productDetails);
    Order toEntity(OrderDto orderDto);

    // TODO - olha o tamanho desse nome
    ProductListDto orderedProductListToProductListDto(List<OrderedProduct> orderedProducts);
    List<OrderedProduct> productListToOrderedProduct(ProductListDto productsIds);

}
