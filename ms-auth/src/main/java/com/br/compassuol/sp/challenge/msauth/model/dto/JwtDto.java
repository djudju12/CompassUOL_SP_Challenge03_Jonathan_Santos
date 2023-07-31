package com.br.compassuol.sp.challenge.msauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private final String tokenType = "Bearer";
}
