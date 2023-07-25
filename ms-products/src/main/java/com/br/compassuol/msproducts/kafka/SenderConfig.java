package com.br.compassuol.msproducts.kafka;

import com.br.compassuol.msproducts.model.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SenderConfig {

    private KafkaTemplate<String, ProductDto> userKafkaTemplate;

    void sendProduct(ProductDto productDto, String topic) {
        log.info("sending product='{}'", productDto.toString());
        userKafkaTemplate.send("product", productDto);
    }
}
