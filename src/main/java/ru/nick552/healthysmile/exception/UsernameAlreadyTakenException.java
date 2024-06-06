package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends ApiException {

    public UsernameAlreadyTakenException() {
        this("Пользователь с таким именем уже существует");
    }

    public UsernameAlreadyTakenException(String description) {
        this(description, "Username already taken");
    }

    public UsernameAlreadyTakenException(String description, String message) {
        super(HttpStatus.CONFLICT, description, message);
    }
}
