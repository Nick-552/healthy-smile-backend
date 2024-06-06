package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class NoSuchVerificationTokenException extends ApiException {

    public NoSuchVerificationTokenException() {
        this("Токен не найден");
    }

    public NoSuchVerificationTokenException(String description) {
        this(description, "Token not found");
    }

    public NoSuchVerificationTokenException(String description, String message) {
        super(HttpStatus.NOT_FOUND, description, message);
    }
}
