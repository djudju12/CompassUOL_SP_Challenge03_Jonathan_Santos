package com.br.compassuol.gateway.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.security.Key;
import java.util.Objects;

@Component
public class JwtUtils {

    @Value("${app.jwt.Secret}")
    private String jwtSecret;

    /**
     *
     * @param serverWebExchange
     * @return apenas o JWT do request
     */
    public String getTokenFromServerWebExchange(ServerWebExchange serverWebExchange) {
//        List<String> authHeader = serverWebExchange.getRequest()
//                        .getHeaders()
//                        .get(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || authHeader.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token not found");
//        }

        // TODO - checar essa excessao
        String token = Objects.requireNonNull(serverWebExchange.getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                        .get(0);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");
    }

    public boolean tokenIsValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            // TODO - Criar uma exception customizada
            throw new RuntimeException(e.getMessage());
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
}
