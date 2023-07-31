package com.br.compassuol.gateway.utils;

import com.br.compassuol.gateway.exceptions.types.InvalidJwtTokenException;
import com.br.compassuol.gateway.exceptions.types.UnathorizedJwtTokenException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class JwtUtilsTest {


    private final JwtUtils jwtUtils = new JwtUtils();

    private final String TOKEN = "Bearer foo";


    @Test
    void getTokenFromServerWebExchange_ReceivesValidToken_ReturnsToken() {
        // given

        MockServerHttpRequest request = MockServerHttpRequest
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, TOKEN)
                .build();

        MockServerWebExchange servletRequest = MockServerWebExchange.from(request);

        // when
        String returnedToken = jwtUtils.getTokenFromServerWebExchange(servletRequest);

        //then
        assertThat(returnedToken).isEqualTo(TOKEN.substring("Bearer ".length()));

    }

    @Test
    void getTokenFromServerWebExchange_ReceivesNoToken_ThrowsException() {
        // given
        MockServerHttpRequest request = MockServerHttpRequest
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, "")
                .build();

        MockServerWebExchange servletRequest = MockServerWebExchange.from(request);

        // when then
        assertThatExceptionOfType(InvalidJwtTokenException.class).isThrownBy(
                () -> jwtUtils.getTokenFromServerWebExchange(servletRequest)
        );

    }

    @Test
    void tokenIsValid_ReceivesInvalidToken_ThrowsException() {
        // when then
        assertThatExceptionOfType(UnathorizedJwtTokenException.class).isThrownBy(
                () -> jwtUtils.validateToken(TOKEN)
        );
    }
}