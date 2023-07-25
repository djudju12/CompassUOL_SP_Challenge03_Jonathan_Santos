package com.br.compassuol.sp.challenge.msorders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@Slf4j
public class MsOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrdersApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(KafkaTemplate<String, Long> template) {
		return args -> {
			log.info("Sending 100 messages to topic orders");
			for (int i = 0; i < 10000; i++){
				template.send("orders", 1L);
			}
		};
	}
}
