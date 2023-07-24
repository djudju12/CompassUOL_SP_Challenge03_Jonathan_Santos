package com.br.compassuol.sp.challenge.msauth.service;

import com.br.compassuol.sp.challenge.msauth.model.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}

