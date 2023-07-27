package com.br.compassuol.msproducts.exception;

import com.br.compassuol.msproducts.exception.types.ProductIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(MockitoExtension.class)
class ExceptionHandlersTest {

    private final long ID = 999L;
    private final ExceptionHandlers exceptionHandlers = new ExceptionHandlers();

    @Test
    void handleNotFoundException() {
        //given
        ProductIdNotFoundException exception = new ProductIdNotFoundException(ID);

        //when
        ResponseEntity<ErrorResponse> response = exceptionHandlers.handleNotFoundException(exception);

        //then
        assertThat(response).isNotNull();
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
        );

    }
    @Test
    void handleMethodArgumentNotValid() {
    }
}