package com.br.compassuol.sp.challenge.msorders.kafka;

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
    @Value("${spring.my-topics.user-send-topic}")
    private String userSendTopic;

    @Value("${spring.my-topics.user-reply-topic}")
    private String userReplyTopic;

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
    @Bean
    public NewTopic userSendTopic() {
        return TopicBuilder.name(userSendTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userReplyTopic() {
        return TopicBuilder.name(userReplyTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
