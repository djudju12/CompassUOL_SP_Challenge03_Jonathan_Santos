package com.br.compassuol.sp.challenge.msauth.jwt;

import com.br.compassuol.sp.challenge.msauth.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final JwtUtils jwtUtils = new JwtUtils();

    @Test
    void generateJwtToken_ReceivesAuthentication_ReturnsToken() {
        // given
        Authentication mockedAuth = Mockito.mock(Authentication.class);
        when(mockedAuth.getName()).thenReturn("username");
        final String testKey = jwtUtils.getTestSecret();

        // when
        String token = jwtTokenProvider.generateJwtToken(mockedAuth);

        // then
        assertThat(token).isNotNull();

        Claims claims = jwtUtils.getClaims(token);
        assertAll(
                () -> assertEquals("username", claims.getSubject()),
                () -> assertNotNull(claims.getIssuedAt()),
                () -> assertNotNull(claims.getExpiration()));

    }
}