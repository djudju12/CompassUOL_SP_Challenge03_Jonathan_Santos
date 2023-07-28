package com.br.compassuol.sp.challenge.msorders.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaTemplateConfig {

    @Bean
    public ReplyingKafkaTemplate<Long, String, Object> replyingTemplate(
            ProducerFactory<Long, String> pf,
            ConcurrentMessageListenerContainer<Long, Object> repliesContainer) {

        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }

    // TODO - group id
    @Bean
    public ConcurrentMessageListenerContainer<Long, Object> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<Long, Object> containerFactory) {

        ConcurrentMessageListenerContainer<Long, Object> repliesContainer =
                containerFactory.createContainer("kReplies");
        repliesContainer.getContainerProperties().setGroupId("products-group");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }
}
