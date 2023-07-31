package com.br.compassuol.sp.challenge.msauth.jwt;

import com.br.compassuol.sp.challenge.msauth.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final JwtUtils jwtUtils = new JwtUtils();

    private final String TEST_SECRET = jwtUtils.getTestSecret();

    private final long TEST_EXP = jwtUtils.getTestExpiration();

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void generateJwtToken_ReceivesAuthentication_ReturnsToken() {
        // given
        Authentication mockedAuth = Mockito.mock(Authentication.class);
        when(mockedAuth.getName()).thenReturn("username");

        // when
        String token = jwtTokenProvider.generateJwtToken(mockedAuth, TEST_SECRET, TEST_EXP);

        // then
        assertThat(token).isNotNull();

        Claims claims = jwtUtils.getClaims(token);
        assertAll(
                () -> assertEquals("username", claims.getSubject()),
                () -> assertNotNull(claims.getIssuedAt()),
                () -> assertNotNull(claims.getExpiration())
        );

    }
}