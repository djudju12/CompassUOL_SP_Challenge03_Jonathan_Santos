package com.br.compassuol.sp.challenge.msorders.controller;

import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    private final ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.my-topics.send-topic}")
    private String sendTopic;

    public OrdersController(ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    @SneakyThrows
    public String getOrders(@RequestBody ProductListDto productListDto) {
        log.info("Received request: {}", productListDto);
        var result = sendString(objectMapper.writeValueAsString(productListDto));
        log.info("Result message: {}", result);
        return "Orders";
    }

    public Object sendString(String string) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<Long, String> record = new ProducerRecord<>(sendTopic, 1L, string);
        RequestReplyFuture<Long, String, Object> replyFuture = kafkaTemplate.sendAndReceive(record);
        SendResult<Long, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<Long, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }
}
