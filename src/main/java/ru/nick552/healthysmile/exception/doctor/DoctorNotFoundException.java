package ru.nick552.healthysmile.exception.doctor;

import org.springframework.http.HttpStatus;
import ru.nick552.healthysmile.exception.ApiException;

public class DoctorNotFoundException extends ApiException {

    public DoctorNotFoundException() {
        this("Доктор не найден");
    }

    public DoctorNotFoundException(String description) {
        this(description, "Doctor not found");
    }

    public DoctorNotFoundException(String description, String message) {
        super(HttpStatus.NOT_FOUND, description, message);
    }
}
