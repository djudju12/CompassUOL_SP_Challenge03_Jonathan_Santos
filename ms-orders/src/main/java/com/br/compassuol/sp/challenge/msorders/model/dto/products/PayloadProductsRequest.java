package com.br.compassuol.sp.challenge.msorders.model.dto.products;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayloadProductsRequest {
    @NotEmpty(message = "Products ids is required")
    @Schema(example = "[1, 2, 3]")
    List<Long> ids;
}