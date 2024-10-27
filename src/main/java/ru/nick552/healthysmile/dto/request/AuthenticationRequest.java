package ru.nick552.healthysmile.dto.request;

public record AuthenticationRequest(
        String username,
        String password
) {}
