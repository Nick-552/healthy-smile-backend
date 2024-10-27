package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nick552.healthysmile.dto.request.AuthenticationRequest;
import ru.nick552.healthysmile.dto.request.RegistrationRequest;
import ru.nick552.healthysmile.dto.response.AuthResponse;
import ru.nick552.healthysmile.model.UserInfo;
import ru.nick552.healthysmile.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin
@Log4j2
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistrationRequest registerUserDto) {
        log.info("Register user {}", registerUserDto);
        var user = authenticationService.signUp(registerUserDto);
        log.info("User registered with id {}", user.id());
        return ResponseEntity.ok()
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Login user {}", authenticationRequest.username());
        var user = authenticationService.authenticate(authenticationRequest);
        log.info("User logged in with id {}", user.id());
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfo> getUser(@RequestHeader("Authorization") String token) {
        var user = authenticationService.getUser(token.split(" ")[1]);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserInfo createUser(@RequestBody RegistrationRequest user) {
        return authenticationService.createUser(user);
    }
}
