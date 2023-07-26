package com.br.compassuol.sp.challenge.msorders.model.dto.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String zipCode;

    @JsonAlias("logradouro")
    private String street;

    @JsonAlias("complemento")
    private String detailedAddress;

    @JsonAlias("bairro")
    private String district;

    @JsonAlias("localidade")
    private String city;

    @JsonAlias("uf")
    private String state;
}
