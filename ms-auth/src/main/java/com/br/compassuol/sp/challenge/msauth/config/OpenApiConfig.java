package com.br.compassuol.sp.challenge.msauth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {@Server(url = "http://localhost:8765/auth")}
        , info = @Info(title = "Authentication Endpoint", version = "1.0", description = "Token API Endpoint v1.0")
)
public class OpenApiConfig {
}
