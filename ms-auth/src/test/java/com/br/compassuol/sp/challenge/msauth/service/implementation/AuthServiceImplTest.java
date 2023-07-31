package com.br.compassuol.sp.challenge.msauth.service.implementation;

import com.br.compassuol.sp.challenge.msauth.jwt.JwtTokenProvider;
import com.br.compassuol.sp.challenge.msauth.model.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private final String TEST_TOKEN = "Bearer foo";

    @Test
    void login_ReceivesValidDto_ReturnToken() {
        // given
        Authentication mockedAuth = Mockito.mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(mockedAuth);
        given(jwtTokenProvider.generateJwtToken(mockedAuth, null, 0L))
                .willReturn(TEST_TOKEN);

        //when
        String token = authService.login(new LoginDto());

        //then
        then(authenticationManager).should().authenticate(any(UsernamePasswordAuthenticationToken.class));
        then(jwtTokenProvider).should().generateJwtToken(mockedAuth, null, 0L);
        assertThat(token).isNotNull();
        assertThat(token).isEqualTo(TEST_TOKEN);
    }

}