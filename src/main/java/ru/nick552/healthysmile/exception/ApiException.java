package ru.nick552.healthysmile.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatusCode httpStatus;

    private final String description;

    public ApiException(HttpStatusCode httpStatus, String description, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
