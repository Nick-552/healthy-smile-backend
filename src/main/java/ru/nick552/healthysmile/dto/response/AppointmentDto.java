package ru.nick552.healthysmile.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AppointmentDto(
        UUID id,
        UUID doctorId,
        UUID patientId,
        OffsetDateTime startDateTime,
        Integer duration
) {
}
