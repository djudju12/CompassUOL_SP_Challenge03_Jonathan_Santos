package com.br.compassuol.sp.challenge.msorders.model.dto.orders;

import com.br.compassuol.sp.challenge.msorders.model.enums.OrderStatus;
import com.br.compassuol.sp.challenge.msorders.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateOrderDto {
    @ValueOfEnum(enumClass = OrderStatus.class, message = "Invalid status")
    @Schema(description = "Order status", example = "PENDING")
    private String status;
}
