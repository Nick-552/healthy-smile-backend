package ru.nick552.healthysmile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nick552.healthysmile.dto.response.ApiErrorResponse;
import ru.nick552.healthysmile.exception.ApiException;

import java.util.Arrays;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
        return createApiErrorResponseEntity(
                e,
                e.getHttpStatus(),
                e.getDescription()
        );
    }


    private ResponseEntity<ApiErrorResponse> createApiErrorResponseEntity(
            Exception e,
            HttpStatusCode status,
            String description
    ) {
        return ResponseEntity.status(status).body(
                new ApiErrorResponse(
                        description,
                        HttpStatus.valueOf(status.value()),
                        e.getClass().getName(),
                        e.getMessage(),
                        Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
                )
        );
    }
}
