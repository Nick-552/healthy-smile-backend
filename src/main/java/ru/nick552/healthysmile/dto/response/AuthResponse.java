package ru.nick552.healthysmile.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nick552.healthysmile.model.Role;

import java.util.UUID;

public record AuthResponse (
        String username,
        UUID id,
        @JsonProperty("jwt_token") String jwtToken,
        Role role
) {
}
