package ru.nick552.healthysmile.exception;

import org.springframework.http.HttpStatus;

public class NoSuchAppointmentException extends ApiException {

    public NoSuchAppointmentException() {
        this("Слот не найден");
    }

    public NoSuchAppointmentException(String description) {
        this(description, "Appointment not found");
    }

    public NoSuchAppointmentException(String description, String message) {
        super(HttpStatus.NOT_FOUND, description, message);
    }
}
