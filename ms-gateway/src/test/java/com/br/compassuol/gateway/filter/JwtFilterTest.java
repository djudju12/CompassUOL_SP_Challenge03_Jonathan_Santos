package com.br.compassuol.gateway.filter;

import com.br.compassuol.gateway.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = {
        "spring.cloud.gateway.routes[0].id=test",
        "spring.cloud.gateway.routes[0].uri=http://localhost:6969",
        "spring.cloud.gateway.routes[0].predicates[0]=Path=/test/**",
        "spring.cloud.gateway.routes[0].filters[0]=JwtFilter",
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({MockitoExtension.class})
class JwtFilterTest {

    @Autowired
    private ApplicationContext context;

   @Test
    void jwtFilter_ReceivesToken_Unauthorized() {
        WebTestClient client = WebTestClient.bindToApplicationContext(this.context)
                .build();

        client.get().uri("/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer bar")
                .exchange().expectStatus().isUnauthorized();
    }
    @Test
    void jwtFilter_ReceivesMalformedToken_Forbbiden() {
        WebTestClient client = WebTestClient.bindToApplicationContext(this.context)
                .build();

        client.get().uri("/test")
                .header(HttpHeaders.AUTHORIZATION, "Foo bar")
                .exchange().expectStatus().isForbidden();
    }


}