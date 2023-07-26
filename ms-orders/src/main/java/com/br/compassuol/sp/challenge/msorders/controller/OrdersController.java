package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.feign.AddressProxy;
import com.br.compassuol.sp.challenge.msorders.model.dto.address.AddressResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.DetailedOrderDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.orders.OrderDto;
import com.br.compassuol.sp.challenge.msorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    private final OrderService orderService;
    private final AddressProxy addressProxy;

    public OrdersController(OrderService orderService,
                            AddressProxy addressProxy) {
        this.orderService = orderService;
        this.addressProxy = addressProxy;
    }

    // TODO - validar os parametros
    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<OrderDto>> getProducts(Pageable page) {
        List<OrderDto> orderList = orderService.findAllOrders(page);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<DetailedOrderDto> getProducts(@PathVariable long orderId) {
        DetailedOrderDto orderDto = orderService.findWithDetails(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
    }

    @GetMapping("/teste/{cep}")
    public ResponseEntity<AddressResponse> teste(@PathVariable String cep) {
        log.info("cep: {}", cep);
        AddressResponse response = addressProxy.getAddressByCep(cep);
        log.info("response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
