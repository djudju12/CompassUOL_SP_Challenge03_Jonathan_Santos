package com.br.compassuol.msproducts.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductsListener {

//    @Value("${spring.kafka.topic2}")
//    private String orderTopic;

    @KafkaListener(topics = "orders", groupId = "ms-products")
    void listener(Long id) {
        log.debug("Received Message: " + id);
    }
}
