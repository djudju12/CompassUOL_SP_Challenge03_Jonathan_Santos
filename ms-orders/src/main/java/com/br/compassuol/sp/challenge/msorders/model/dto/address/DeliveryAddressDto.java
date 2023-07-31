package com.br.compassuol.sp.challenge.msorders.model.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class DeliveryAddressDto extends AddressResponse {

    @NotBlank(message = "Number is required")
    private String number;

}
