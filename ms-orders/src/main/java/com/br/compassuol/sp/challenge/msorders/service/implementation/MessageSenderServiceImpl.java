package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.kafka.MessageSender;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.dto.users.MessageResponse;
import com.br.compassuol.sp.challenge.msorders.service.MessageSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSenderServiceImpl implements MessageSenderService {

    private final MessageSender messageSender;
    private final ObjectMapper objectMapper;

    @Value("${spring.my-topics.send-topic}")
    private String sendTopic;

    @Value("${spring.my-topics.user-send-topic}")
    private String userSendTopic;

    public MessageSenderServiceImpl(MessageSender messageSender,
                                    ObjectMapper objectMapper) {
        this.messageSender = messageSender;
        this.objectMapper = objectMapper;
    }

    // TODO - Melhorar isso
    @Override
    @SneakyThrows
    public PayloadProductsResponse getProductsDescription(PayloadProductsRequest payloadProductsRequest) {
        String request = objectMapper.writeValueAsString(payloadProductsRequest);
        Object result = messageSender.getResult(request, sendTopic);
        return objectMapper.readValue(String.valueOf(result), PayloadProductsResponse.class);
    }

    // TODO - Melhorar isso
    @Override
    @SneakyThrows
    public boolean userExists(Long userId) {
        String request = objectMapper.writeValueAsString(userId);
        Object result = messageSender.getResult(request, userSendTopic);
        MessageResponse payload = objectMapper.readValue(String.valueOf(result), MessageResponse.class);
        return payload.isExists();
    }

}
