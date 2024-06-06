package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        this("Пользователь не найден");
    }

    public UserNotFoundException(String description) {
        this(description, "User not found");
    }

    public UserNotFoundException(String description, String message) {
        super(HttpStatus.NOT_FOUND, description, message);
    }
}
