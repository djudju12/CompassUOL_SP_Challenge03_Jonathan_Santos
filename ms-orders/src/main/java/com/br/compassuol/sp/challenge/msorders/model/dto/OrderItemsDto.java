package com.br.compassuol.sp.challenge.msorders.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto implements Serializable {
    private List<Long> items;
}
