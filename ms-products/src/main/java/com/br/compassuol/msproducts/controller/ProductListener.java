package com.br.compassuol.msproducts.controller;

import com.br.compassuol.msproducts.model.dto.PayloadProducts;
import com.br.compassuol.msproducts.model.dto.ProductDto;
import com.br.compassuol.msproducts.model.dto.ProductListDto;
import com.br.compassuol.msproducts.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductListener {

    private final ObjectMapper objectMapper;

    private final ProductService productService;

    public ProductListener(ObjectMapper objectMapper,
                           ProductService productService) {
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @KafkaListener(topics = "products-send")
    @SendTo
    // TODO excessoes
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        ProductListDto productListDto = objectMapper.readValue(String.valueOf(consumerRecord.value()), ProductListDto.class);
        log.info("Received request: {}", productListDto);
        PayloadProducts payload = productService.findAllById(productListDto.getIds());
        return MessageBuilder.withPayload( objectMapper.writeValueAsString(payload) )
                .build();
    }

}
