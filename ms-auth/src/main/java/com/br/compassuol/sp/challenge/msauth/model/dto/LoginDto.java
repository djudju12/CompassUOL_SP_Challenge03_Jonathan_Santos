package com.br.compassuol.sp.challenge.msauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    // TODO - adicionar validacao
    private String username;
    private String password;
}
