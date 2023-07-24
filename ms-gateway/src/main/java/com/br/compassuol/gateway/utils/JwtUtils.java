package com.br.compassuol.gateway.utils;

import com.br.compassuol.gateway.exceptions.types.InvalidJwtTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.security.Key;
import java.util.List;

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
        List<String> authHeader = serverWebExchange.getRequest()
                        .getHeaders()
                        .get(HttpHeaders.AUTHORIZATION);

        // TODO - checar essa excessao
        if (authHeader == null || authHeader.isEmpty()) {
            throw new InvalidJwtTokenException();
        }

        String token = authHeader.get(0);
        if (!token.startsWith("Bearer ")) {
            throw new InvalidJwtTokenException();
        }

        return token.substring("Bearer ".length());
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
