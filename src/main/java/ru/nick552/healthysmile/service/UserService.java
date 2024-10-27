package ru.nick552.healthysmile.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.nick552.healthysmile.model.UserInfo;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    List<UserInfo> listAll();

    void makeAdmin(UUID id);

    UserInfo getUserById(UUID id);
}
