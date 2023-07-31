package com.br.compassuol.gateway.filter;

import com.br.compassuol.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Token from request
            log.info("Headers: {}", exchange.getRequest().getHeaders());
            String token = jwtUtils.getTokenFromServerWebExchange(exchange);

            // If token is unauthorized throws an exception
            jwtUtils.validateToken(token);

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // ...
    }
}