package com.br.compassuol.msproducts.controller;

import com.br.compassuol.msproducts.model.dto.CartDto;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.rabbitmq.RabbitConfig;
import com.br.compassuol.msproducts.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductListener {

    private final ProductService productService;

    final
    RabbitTemplate rabbitTemplate;

    public ProductListener(ProductService productService,
                           RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitConfig.RPC_MESSAGE_QUEUE)
    public void process(List<CartDto> dtos) {
        log.info("Received message from RabbitMQ: {}", dtos);
        List<ProductDto> products = dtos.stream()
                .map(dto -> productService.findProductById(dto.getProductId()))
                .toList();

        rabbitTemplate.convertSendAndReceive(RabbitConfig.RPC_EXCHANGE, RabbitConfig.RPC_REPLY_MESSAGE_QUEUE, products);
    }

}
