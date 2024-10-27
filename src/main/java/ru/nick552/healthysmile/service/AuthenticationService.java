package ru.nick552.healthysmile.service;

import ru.nick552.healthysmile.dto.request.AuthenticationRequest;
import ru.nick552.healthysmile.dto.request.RegistrationRequest;
import ru.nick552.healthysmile.dto.response.AuthResponse;
import ru.nick552.healthysmile.model.UserInfo;

public interface AuthenticationService {

    AuthResponse authenticate(AuthenticationRequest request);

    AuthResponse signUp(RegistrationRequest request);

    UserInfo getUser(String token);

    UserInfo createUser(RegistrationRequest request);
}
