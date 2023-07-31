package com.br.compassuol.sp.challenge.msauth.controller;

import com.br.compassuol.sp.challenge.msauth.model.dto.JwtDto;
import com.br.compassuol.sp.challenge.msauth.model.dto.LoginDto;
import com.br.compassuol.sp.challenge.msauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentications")
public class SecurityController {

    private final AuthService authService;

    public SecurityController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Create API Token")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "JWT Token created"
                    , content = @Content(schema = @Schema(implementation = JwtDto.class))),
        @ApiResponse(responseCode = "4XX", description = "Unauthorized | Bad Request"
                    , content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtDto jwtAuthResponse = new JwtDto();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }

}
