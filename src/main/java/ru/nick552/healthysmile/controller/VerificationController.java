package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nick552.healthysmile.service.VerificationService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/verification")
@RequiredArgsConstructor
@CrossOrigin
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        verificationService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }
}
