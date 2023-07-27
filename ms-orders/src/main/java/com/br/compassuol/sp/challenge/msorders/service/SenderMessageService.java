package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.ProductDto;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;

import java.util.List;

public interface SenderMessageService {
    PayloadProductsResponse getProductsDescription(PayloadProductsRequest payloadProductsRequest);
    boolean userExists(Long userId);
}
