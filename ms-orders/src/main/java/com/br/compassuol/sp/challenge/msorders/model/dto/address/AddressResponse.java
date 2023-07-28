package com.br.compassuol.sp.challenge.msorders.model.dto.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AddressResponse {

    @JsonIgnore
    private long id;

    @JsonAlias("cep")
    @NotBlank(message = "Zip code is required")
    @Schema(example = "01001000", pattern = "[0-9]{5}-?[0-9]{3}")
    private String zipCode;

    @JsonAlias("logradouro")
    @Schema(example = "Praça da Sé")
    private String street;

    @JsonAlias("complemento")
    @Schema(example = "lado ímpar")
    private String detailedAddress;

    @JsonAlias("bairro")
    @Schema(example = "Sé")
    private String district;

    @JsonAlias("localidade")
    @Schema(example = "São Paulo")
    private String city;

    @JsonAlias("uf")
    @Schema(example = "SP")
    private String state;
}
