package com.br.compassuol.sp.challenge.msorders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@SpringBootApplication
@Slf4j
@EnableKafka
public class MsOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrdersApplication.class, args);
	}

	// TODO - verificar a possibilidade de usar auto-config
	@Bean
	public ReplyingKafkaTemplate<Long, String, Object> replyingTemplate(
			ProducerFactory<Long, String> pf,
			ConcurrentMessageListenerContainer<Long, Object> repliesContainer) {

		return new ReplyingKafkaTemplate<>(pf, repliesContainer);
	}

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
