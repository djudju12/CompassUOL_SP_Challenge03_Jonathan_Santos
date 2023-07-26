package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.model.dto.PayloadProducts;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;
import com.br.compassuol.sp.challenge.msorders.service.SenderMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SenderMessageServiceImpl implements SenderMessageService {

    private final ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.my-topics.send-topic}")
    private String sendTopic;

    public SenderMessageServiceImpl(ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // TODO - Melhorar isso
    @Override
    @SneakyThrows
    public List<ProductDto> getProductsDescription(ProductListDto productListDto) {
        String jsonContent = objectMapper.writeValueAsString(productListDto);
        ProducerRecord<Long, String> record = new ProducerRecord<>(sendTopic, 1L, jsonContent);
        RequestReplyFuture<Long, String, Object> replyFuture = kafkaTemplate.sendAndReceive(record);
        SendResult<Long, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<Long, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);

        Object result = consumerRecord.value();
        PayloadProducts payload = objectMapper.readValue(String.valueOf(result), PayloadProducts.class);
        return payload.getProducts();
    }
}
