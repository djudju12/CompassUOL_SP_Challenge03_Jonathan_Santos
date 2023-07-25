package com.br.compassuol.sp.challenge.msauth.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginDto {
    // TODO - adicionar validacao
    private String username;
    private String password;
}
