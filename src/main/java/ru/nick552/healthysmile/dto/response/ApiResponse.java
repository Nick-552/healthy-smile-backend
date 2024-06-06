package ru.nick552.healthysmile.dto.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
public class ApiResponse {

    private final HttpStatusCode httpStatus;

    private final String description;
}
