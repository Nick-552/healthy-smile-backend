package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nick552.healthysmile.model.UserInfo;
import ru.nick552.healthysmile.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<UserInfo> getUsers() {
        return userService.listAll();
    }
}
