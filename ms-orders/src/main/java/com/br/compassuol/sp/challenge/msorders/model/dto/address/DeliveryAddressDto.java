package com.br.compassuol.sp.challenge.msorders.model.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class DeliveryAddressDto extends AddressResponse {

    @NotBlank(message = "Street is required")
    @NotBlank(message = "Number code is required")
    private String number;

}
