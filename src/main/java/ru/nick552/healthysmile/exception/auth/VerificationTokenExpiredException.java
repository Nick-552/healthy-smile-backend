package ru.nick552.healthysmile.exception.auth;

import org.springframework.http.HttpStatus;
import ru.nick552.healthysmile.exception.ApiException;

public class VerificationTokenExpiredException extends ApiException {

    public VerificationTokenExpiredException() {
        this("Токен устарел");
    }

    public VerificationTokenExpiredException(String description) {
        this(description, "Token has expired");
    }

    public VerificationTokenExpiredException(String description, String message) {
        super(HttpStatus.UNAUTHORIZED, description, message);
    }
}
