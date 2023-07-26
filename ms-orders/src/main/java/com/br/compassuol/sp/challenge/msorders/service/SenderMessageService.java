package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;

import java.util.List;

public interface SenderMessageService {
    List<ProductDto> getProductsDescription(PayloadProductsRequest payloadProductsRequest);
}
