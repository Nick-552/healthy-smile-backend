package ru.nick552.healthysmile.exception.patient;

import org.springframework.http.HttpStatus;
import ru.nick552.healthysmile.exception.ApiException;

public class PatientNotFoundException extends ApiException {

    public PatientNotFoundException() {
        this("Пользователь не найден");
    }

    public PatientNotFoundException(String description) {
        this(description, "User not found");
    }

    public PatientNotFoundException(String description, String message) {
        super(HttpStatus.NOT_FOUND, description, message);
    }
}
