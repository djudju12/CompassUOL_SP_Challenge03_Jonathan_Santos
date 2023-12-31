package com.br.compassuol.sp.challenge.msauth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtils {

    private final String testSecret = "2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b";
    private final long testExpirationMs = 1000 * 60 * 60;
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .setAllowedClockSkewSeconds(1)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(testSecret)
        );
    }

    public String getTestSecret() {
        return testSecret;
    }

    public long getTestExpiration() {
        return testExpirationMs;
    }
}
