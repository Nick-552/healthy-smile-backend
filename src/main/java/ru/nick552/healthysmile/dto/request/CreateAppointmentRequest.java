package ru.nick552.healthysmile.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateAppointmentRequest(
    OffsetDateTime startDateTime,
    Integer duration,
    int count
) {
}
