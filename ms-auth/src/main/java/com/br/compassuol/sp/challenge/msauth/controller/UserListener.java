package com.br.compassuol.sp.challenge.msauth.controller;

import com.br.compassuol.sp.challenge.msauth.model.dto.MessageResponse;
import com.br.compassuol.sp.challenge.msauth.repository.UserEntityRepository;
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
public class UserListener {
    private final ObjectMapper objectMapper;
    private final UserEntityRepository userService;

    public UserListener(ObjectMapper objectMapper,
                        UserEntityRepository userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @KafkaListener(topics = "users-send")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        Long id = objectMapper.readValue(String.valueOf(consumerRecord.value()), Long.class);
        boolean exists = userService.existsById(id);
        MessageResponse message = new MessageResponse()
                .setMessage("default message")
                .setUserId(id)
                .setExists(exists);

        return MessageBuilder.withPayload( objectMapper.writeValueAsString(message) )
                .build();
    }
}
