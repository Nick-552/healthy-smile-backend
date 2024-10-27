package ru.nick552.healthysmile.service.impl.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.UserEntity;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.exception.user.UserNotFoundException;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.model.UserInfo;
import ru.nick552.healthysmile.service.UserService;

import java.util.List;
import java.util.UUID;

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


    @Override
    @Transactional
    public void makeAdmin(UUID id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(Role.ADMIN);
    }

    @Override
    public UserInfo getUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .getUserInfo();
    }
}
