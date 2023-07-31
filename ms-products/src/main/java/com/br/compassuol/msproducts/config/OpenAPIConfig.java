package com.br.compassuol.msproducts.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {@Server(url = "http://localhost:8765/products")}
        , info = @Info(title = "Products API", version = "1.0", description = "Documentation products API v1.0")
)
@SecurityScheme(
        name = "Authorization"
        , type = SecuritySchemeType.APIKEY
        , in = SecuritySchemeIn.HEADER
        , description = "Bearer {token}"
)
public class OpenAPIConfig {
}
