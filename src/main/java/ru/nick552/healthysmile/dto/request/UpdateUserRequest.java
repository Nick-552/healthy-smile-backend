package ru.nick552.healthysmile.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.nick552.healthysmile.model.Role;

import java.util.UUID;

public record UpdateUserRequest(
        @NotNull
        UUID id,
        String username,
        String name,
        String surname,
        String patronymic,
        String email,
        String phone,
        Role role
) {
}
