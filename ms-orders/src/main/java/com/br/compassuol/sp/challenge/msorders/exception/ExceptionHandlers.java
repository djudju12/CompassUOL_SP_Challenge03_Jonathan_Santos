package com.br.compassuol.sp.challenge.msorders.exception;

import com.br.compassuol.sp.challenge.msorders.exception.types.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            OrderIdNotFoundException.class
            , UserIdNotFoundException.class
            , AddressNotFoundException.class
    })
    protected ResponseEntity<ErrorResponse> handleNotFoundException(Exception exc) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CancelOrderMisuseException.class
            , PropertyReferenceException.class
    }) // Generic bad request
    protected ResponseEntity<ErrorResponse> handleMisuseOfCancel(Exception exc) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
           /* You cand add more exceptions,
            * but first you need to create an Exception type with Map<Long, String> errors
            * and extends.
            */
            ProductErrorResponseException.class
    })
    protected ResponseEntity<ErrorResponse> handleErrorOnProductResponse(ProductErrorResponseException exc) {
        Map<Long, String> errors = exc.getErrors();
        String message = "Total errors: " + errors.size();

        // List all errors
        message += ". Errors: " + errors.entrySet()
                                        .stream()
                                        .map(e -> e.getKey() + ": " + e.getValue())
                                        .toList();

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /*
     * Handle all validation exceptions not handled by handleMethodArgumentNotValid
     * I think this happens when the validation is done in the service or other layer (not controller)
     * I don't know if this is the best way to handle this, but it works
     */
    @ExceptionHandler({
            ConstraintViolationException.class
    })
    protected ResponseEntity<ErrorResponse> handleInvalidInput(ConstraintViolationException exc) {
        Set<ConstraintViolation<?>> violations = exc.getConstraintViolations();
        String message = "Total errors: " + violations.size() +
                ". Errors: " + violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override // Handle @Validated in controller layer
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String message =
                "Total errors: " + ex.getFieldErrorCount() +
                        ". Errors: " + ex.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();

        ErrorResponse error = new ErrorResponse(status.value(), message);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }
}