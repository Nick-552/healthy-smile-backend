package ru.nick552.healthysmile.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.nick552.healthysmile.model.UserInfo;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserInfo> listAll();
}
