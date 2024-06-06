package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class DoctorAlreadyExistsException extends ApiException {

    public DoctorAlreadyExistsException() {
        this("Доктор уже существует");
    }

    public DoctorAlreadyExistsException(String description) {
        this(description, "Doctor already exists");
    }

    public DoctorAlreadyExistsException(String description, String message) {
        super(HttpStatus.CONFLICT, description, message);
    }
}
