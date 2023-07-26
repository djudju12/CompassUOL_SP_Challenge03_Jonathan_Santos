package com.br.compassuol.sp.challenge.msorders.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ProductListDto {
    List<Long> ids;
}