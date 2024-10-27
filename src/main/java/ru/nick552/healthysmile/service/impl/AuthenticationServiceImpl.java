package ru.nick552.healthysmile.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.UserEntity;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.dto.request.AuthenticationRequest;
import ru.nick552.healthysmile.dto.request.RegistrationRequest;
import ru.nick552.healthysmile.dto.response.AuthResponse;
import ru.nick552.healthysmile.exception.user.UserNotFoundException;
import ru.nick552.healthysmile.exception.user.UsernameAlreadyTakenException;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.model.UserInfo;
import ru.nick552.healthysmile.service.AuthenticationService;
import ru.nick552.healthysmile.service.JwtService;
import ru.nick552.healthysmile.service.VerificationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final VerificationService verificationService;

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(UserNotFoundException::new);
        return new AuthResponse(
                user.getUsername(),
                user.getId(),
                jwtService.generateToken(user.getUsername(), user.getId()),
                user.getRole()
        );
    }

    @Override
    @Transactional
    public AuthResponse signUp(RegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyTakenException();
        }
        var user = userRepository.save(
                UserEntity.builder()
                        .email(request.email())
                        .phone(request.phone())
                        .enabled(true)
                        .name(request.name())
                        .surname(request.surname())
                        .patronymic(request.patronymic())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.USER)
                        .build()
        );
        String token = verificationService.generateVerificationToken(user);
        verificationService.sendEmailVerification(token, user.getEmail());
        return new AuthResponse(
                user.getUsername(),
                user.getId(),
                jwtService.generateToken(user.getUsername(), user.getId()),
                Role.USER
        );
    }

    @Override
    public UserInfo getUser(String token) {
        var user = userRepository.findByUsername(jwtService.extractUsername(token))
                .orElseThrow(UserNotFoundException::new);
        return new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    @Override
    public UserInfo createUser(RegistrationRequest request) {
        var userEntity = UserEntity.builder()
                .email(request.email())
                .phone(request.phone())
                .enabled(true)
                .name(request.name())
                .surname(request.surname())
                .patronymic(request.patronymic())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        var user = userRepository.save(userEntity);
        return user.getUserInfo();
    }
}
