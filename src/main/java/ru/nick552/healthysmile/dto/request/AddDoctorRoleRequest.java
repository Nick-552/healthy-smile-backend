package ru.nick552.healthysmile.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddDoctorRoleRequest(
        @NotNull
        @JsonProperty("user_id")
        UUID userId,
        @NotNull
        String specialization,
        @NotNull
        String info,
        @NotNull
        String photo
) {
}
