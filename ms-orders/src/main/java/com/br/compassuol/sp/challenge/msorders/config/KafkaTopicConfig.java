package com.br.compassuol.sp.challenge.msorders.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.my-topics.send-topic}")
    private String sendTopic;

    @Value("${spring.my-topics.reply-topic}")
    private String replyTopic;

    @Bean
    public NewTopic sendTopic() {
        return TopicBuilder.name(sendTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic() {
        return TopicBuilder.name(replyTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
