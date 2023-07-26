package com.br.compassuol.msproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
//@EnableKafka
public class MsProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductsApplication.class, args);
    }

}
