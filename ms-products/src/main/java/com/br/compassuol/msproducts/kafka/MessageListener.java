package com.br.compassuol.msproducts.kafka;

import com.br.compassuol.msproducts.model.dto.ProductListDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    private final ObjectMapper objectMapper;

    public MessageListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "products-send")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        String reversedString = "Server speaking. Received: " + consumerRecord.value();
        ProductListDto productListDto = objectMapper.readValue(String.valueOf(consumerRecord.value()), ProductListDto.class);
        log.info("Received request: {}", productListDto);
        return MessageBuilder.withPayload( reversedString )
                .build();
    }
}
