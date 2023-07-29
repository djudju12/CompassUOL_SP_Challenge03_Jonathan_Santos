package com.br.compassuol.gateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gateway API", version = "1.0", description = "Documentation Gateway API v1.0"))
@Slf4j
public class MsGatewayApplication {

    public MsGatewayApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApplication.class, args);
    }


    final
    Environment environment;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info(Arrays.toString(this.environment.getActiveProfiles()));
        };
    }

}
