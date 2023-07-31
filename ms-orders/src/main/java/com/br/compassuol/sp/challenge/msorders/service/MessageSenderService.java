package com.br.compassuol.sp.challenge.msorders.service;

import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;

public interface MessageSenderService {
    PayloadProductsResponse getProductsDescription(PayloadProductsRequest payloadProductsRequest);
    boolean userExists(Long userId);
}
