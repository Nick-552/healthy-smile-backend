package ru.nick552.healthysmile.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.net.URI;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DoctorInfo(
        UserInfo userInfo,
        String specialization,
        URI photo,
        String info
) {
}
