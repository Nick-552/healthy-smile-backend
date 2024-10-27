package ru.nick552.healthysmile.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateDoctorRequest(
        @NotNull UUID doctorId,
        String specialization,
        String info,
        String photo
) { }
