package com.br.compassuol.sp.challenge.msorders.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    @KafkaListener(topics = "order", groupId = "ms-orders")
    void listener(String data) {
        log.info("Received: {}", data);
    }
}
