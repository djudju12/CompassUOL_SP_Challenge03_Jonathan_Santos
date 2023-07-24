package com.br.compassuol.gateway.filter;

import com.br.compassuol.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
            String token = jwtUtils.getTokenFromServerWebExchange(exchange);
            if (!jwtUtils.tokenIsValid(token))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // ...
    }
}
