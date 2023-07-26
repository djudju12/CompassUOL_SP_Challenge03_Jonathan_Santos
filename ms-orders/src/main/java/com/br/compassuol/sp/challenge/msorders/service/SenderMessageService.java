package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.ProductListDto;

import java.util.List;

public interface SenderMessageService {
    List<ProductDto> getProductsDescription(ProductListDto productListDto);
}
