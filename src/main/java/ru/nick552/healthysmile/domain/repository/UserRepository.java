package ru.nick552.healthysmile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nick552.healthysmile.domain.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String login);

    Boolean existsByUsername(String username);
}
