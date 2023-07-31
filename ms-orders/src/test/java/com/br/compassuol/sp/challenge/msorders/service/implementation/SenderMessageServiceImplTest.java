package com.br.compassuol.sp.challenge.msorders.service.implementation;

import com.br.compassuol.sp.challenge.msorders.kafka.MessageSender;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsRequest;
import com.br.compassuol.sp.challenge.msorders.model.dto.products.PayloadProductsResponse;
import com.br.compassuol.sp.challenge.msorders.model.dto.users.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SenderMessageServiceImplTest {

    @Mock
    private MessageSender messageSender;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MessageSenderServiceImpl senderMessageService;

    @Test
    void getProductsDescription() throws Exception {
        // given
        PayloadProductsRequest request = new PayloadProductsRequest();
        String jsonRequest = "{}";
        Object result = new Object();
        PayloadProductsResponse response = new PayloadProductsResponse();

        given(objectMapper.writeValueAsString(request)).willReturn(jsonRequest);
        given(messageSender.getResult(jsonRequest, null)).willReturn(result);
        given(objectMapper.readValue(String.valueOf(result), PayloadProductsResponse.class))
                .willReturn(response);

        // when
        PayloadProductsResponse resultResponse = senderMessageService.getProductsDescription(request);

        // then
        assertThat(resultResponse).isEqualTo(response);
        then(objectMapper).should().writeValueAsString(request);
        then(messageSender).should().getResult(jsonRequest, null);
        then(objectMapper).should().readValue(String.valueOf(result), PayloadProductsResponse.class);
    }

    @Test
    void userExists() throws Exception {
        // given
        Long request = 10L;
        String jsonRequest = "{}";
        Object result = new Object();
        MessageResponse payload = new MessageResponse();
        payload.setExists(true);

        given(objectMapper.writeValueAsString(request)).willReturn(jsonRequest);
        given(messageSender.getResult(jsonRequest, null)).willReturn(result);
        given(objectMapper.readValue(String.valueOf(result), MessageResponse.class))
                .willReturn(payload);

        // when
        boolean response = senderMessageService.userExists(request);

        // then
        assertThat(response).isEqualTo(payload.isExists());
        then(objectMapper).should().writeValueAsString(request);
        then(messageSender).should().getResult(jsonRequest, null);
        then(objectMapper).should().readValue(String.valueOf(result), MessageResponse.class);
    }

}