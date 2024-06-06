package ru.nick552.healthysmile.model;

import java.util.UUID;

public record UserInfo (
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
