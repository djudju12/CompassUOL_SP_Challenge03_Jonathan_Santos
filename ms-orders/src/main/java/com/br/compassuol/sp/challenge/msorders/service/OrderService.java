package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.UpdateOrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> findAllOrders(Pageable pageable);
    void cancelOrder(long id);
    DetailedOrderDto findWithDetails(long orderId);
    OrderDto updateOrder(long id, UpdateOrderDto orderDto);
}
