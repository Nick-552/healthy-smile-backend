package ru.nick552.healthysmile.dto.request;

import ru.nick552.healthysmile.model.Gender;

public record RegistrationRequest(
        String username,
        String email,
        String phone,
        String password,
        String name,
        String surname,
        String patronymic,
        Gender gender
) {
}
