package ru.nick552.healthysmile.service;

import ru.nick552.healthysmile.domain.entity.UserEntity;

public interface VerificationService {

    void sendEmailVerification(String token, String email);

    void verifyEmail(String token);

    String generateVerificationToken(UserEntity user);
}
