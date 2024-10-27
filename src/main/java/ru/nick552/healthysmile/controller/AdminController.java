package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nick552.healthysmile.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    private final UserService userService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void createAdmin(@PathVariable UUID id) {
        log.info("Create admin {}", id);
        userService.makeAdmin(id);
    }
}
