package com.br.compassuol.sp.challenge.msauth.controller;

import com.br.compassuol.sp.challenge.msauth.config.SecurityConfig;
import com.br.compassuol.sp.challenge.msauth.jwt.JwtAuthEntryPoint;
import com.br.compassuol.sp.challenge.msauth.model.dto.LoginDto;
import com.br.compassuol.sp.challenge.msauth.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityController.class)
@ImportAutoConfiguration(classes = {SecurityConfig.class})
class SecurityControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    private final String TOKEN = "Bearer foo";

    @AfterEach
    void tearDown() {
        reset(authService);
    }

    @Test
    void login_ReceivesValidDto_ReturnOk() throws Exception {
        //given
        given(authService.login(any(LoginDto.class))).willReturn(TOKEN);


        //when then
        mockMvc.perform(post("/auth/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"foo\", \"password\": \"bar\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value(TOKEN));

        then(authService).should().login(any(LoginDto.class));
    }
}