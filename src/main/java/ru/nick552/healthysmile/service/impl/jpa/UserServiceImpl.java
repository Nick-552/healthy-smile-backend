package ru.nick552.healthysmile.service.impl.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.UserEntity;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.exception.UserNotFoundException;
import ru.nick552.healthysmile.model.UserInfo;
import ru.nick552.healthysmile.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserEntity getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }

    @Override
    public List<UserInfo> listAll() {
        return repository.findAll()
                .stream()
                .map(UserEntity::getUserInfo)
                .toList();
    }
}
