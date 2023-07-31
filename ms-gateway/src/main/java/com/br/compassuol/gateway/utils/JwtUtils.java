package com.br.compassuol.gateway.utils;

import com.br.compassuol.gateway.exceptions.types.InvalidJwtTokenException;
import com.br.compassuol.gateway.exceptions.types.UnathorizedJwtTokenException;
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

    /*
     * This function returns just the token from the request
     * without the "Bearer " prefix
     */
    public String getTokenFromServerWebExchange(ServerWebExchange serverWebExchange) {
        List<String> authHeader = serverWebExchange.getRequest()
                .getHeaders()
                .get(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty()) {
            throw new InvalidJwtTokenException();
        }

        String token = authHeader.get(0);
        if (!token.startsWith("Bearer ")) {
            throw new InvalidJwtTokenException();
        }

        // Here we make sure that the bearer shall not pass
        return token.substring("Bearer ".length());
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()

                    // If parse fails means that token is unauthorized
                    .parse(token);
        } catch (Exception e) {
            throw new UnathorizedJwtTokenException(e.getMessage());
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
}