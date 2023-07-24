package com.br.compassuol.sp.challenge.msauth.controller;

import com.br.compassuol.sp.challenge.msauth.model.dto.JwtDto;
import com.br.compassuol.sp.challenge.msauth.model.dto.LoginDto;
import com.br.compassuol.sp.challenge.msauth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final AuthService authService;

    public SecurityController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtDto jwtAuthResponse = new JwtDto();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

}
