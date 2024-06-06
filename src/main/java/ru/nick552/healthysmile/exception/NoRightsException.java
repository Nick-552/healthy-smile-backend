package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class NoRightsException extends ApiException {

    public NoRightsException() {
        this("У вас нет прав для этого действия");
    }

    public NoRightsException(String description) {
        this(description, "No rights");
    }

    public NoRightsException(String description, String message) {
        super(HttpStatus.FORBIDDEN, description, message);
    }
}
