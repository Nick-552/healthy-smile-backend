package ru.nick552.healthysmile.exception.auth;

import org.springframework.http.HttpStatus;
import ru.nick552.healthysmile.exception.ApiException;

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
