package ru.nick552.healthysmile.dto.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiErrorResponse(
        String description,
        HttpStatus code,
        String exceptionName,
        String exceptionMessage,
        List<String> stacktrace
) {
}
