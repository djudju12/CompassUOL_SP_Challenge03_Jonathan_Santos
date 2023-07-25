package com.br.compassuol.msproducts.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topic1}")
    private String topicProduct;

    @Value("${spring.kafka.topic2}")
    private String topicOrders;

    @Bean
    public NewTopic createTopicProduct() {
        return TopicBuilder.name(topicProduct)
                .build();
    }

    @Bean
    public NewTopic createTopicOrders() {
        return TopicBuilder.name(topicOrders)
                .build();
    }
}
