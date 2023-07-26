package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> findAllOrders(int page, int linesPerPage, String direction, String orderBy);
    void cancelOrder(long id);
    DetailedOrderDto findWithDetails(long orderId);
}
