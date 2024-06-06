package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
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
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistrationRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok()
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var user = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserInfo> getUser(@RequestHeader("Authorization") String token) {
        var user = authenticationService.getUser(token.split(" ")[1]);
        return ResponseEntity.ok().body(user);
    }
//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<UserResponse> getUser(@RequestHeader("Authorization") String token) {
//        var user = authenticationService.getUser(token.split(" ")[1]);
//        return ResponseEntity.ok().body(user);
//    }
}
