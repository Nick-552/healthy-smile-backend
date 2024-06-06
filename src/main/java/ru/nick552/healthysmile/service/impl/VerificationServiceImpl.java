package ru.nick552.healthysmile.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.configuration.ApplicationConfig;
import ru.nick552.healthysmile.domain.entity.PatientEntity;
import ru.nick552.healthysmile.domain.entity.UserEntity;
import ru.nick552.healthysmile.domain.entity.VerificationToken;
import ru.nick552.healthysmile.domain.repository.PatientRepository;
import ru.nick552.healthysmile.domain.repository.VerificationTokenRepository;
import ru.nick552.healthysmile.exception.NoSuchVerificationTokenException;
import ru.nick552.healthysmile.exception.UserNotFoundException;
import ru.nick552.healthysmile.exception.VerificationTokenExpiredException;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.service.EmailService;
import ru.nick552.healthysmile.service.VerificationService;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final ApplicationConfig config;

    private final EmailService emailService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PatientRepository patientRepository;

    private String emailVerificationSubject;

    private String emailVerificationLetterText;

    @PostConstruct
    public void init() {
        emailVerificationSubject = "Healthy-Smile email verification";
        String emailVerificationUrl = config.baseUrl() + "/verification/email?token=%s";
        emailVerificationLetterText = "Click on the link to verify your email: " + emailVerificationUrl;
    }

    @Override
    @Transactional
    public String generateVerificationToken(UserEntity user) {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[32];
        random.nextBytes(token);
        var tokenString = Base64.getEncoder().encodeToString(token);
        var verificationToken = new VerificationToken(tokenString, user);
        verificationTokenRepository.save(verificationToken);
        return tokenString;
    }

    @Override
    public void sendEmailVerification(String token, String email) {
        emailService.sendEmail(
                email,                                          // recipient
                emailVerificationSubject,                     // subject
                emailVerificationLetterText.formatted(token) // text
        );
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(NoSuchVerificationTokenException::new);
        if (verificationToken.isExpired()) {
            throw new VerificationTokenExpiredException();
        }
        var user = verificationToken.getUser();
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getRole().isHigherOrEqual(Role.PATIENT)) {
            user.setRole(Role.PATIENT);
        }
        var patient = new PatientEntity();
        patient.setId(user.getId());
        patientRepository.makePatient(patient);
        verificationTokenRepository.delete(verificationToken);
    }
}
