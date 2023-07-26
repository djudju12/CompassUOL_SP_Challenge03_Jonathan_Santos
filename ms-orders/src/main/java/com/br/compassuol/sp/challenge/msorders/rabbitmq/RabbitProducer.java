package com.br.compassuol.sp.challenge.msorders.rabbitmq;

import com.br.compassuol.sp.challenge.msorders.model.dto.CartDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.OrderItemsDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/rabbitmq")
@Slf4j
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/send")
    public List<ProductDto> send(@RequestBody OrderItemsDto cart) {
        log.info("Sending message to RabbitMQ: {}", cart.getItems());
//        Message message = MessageBuilder.withBody(cart.toString().getBytes())
//                .setContentType("application/json")
//                .build();

        Object result = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_REPLY_MESSAGE_QUEUE, cart);
        List<ProductDto> response = new ArrayList<>();
        if (result != null) {
//            String correlationId = message.getMessageProperties().getCorrelationId();

//            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();
            // Access server Message returned id
//            String msgId = (String) headers.get("spring_returned_message_correlation");
//            if (msgId.equals(correlationId)) {
            log.info("Message sent and received successfully. Result: {} ", result);

//                response = new String(result.getBody());
        }
//        }

        return response;
    }
}
