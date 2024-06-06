package ru.nick552.healthysmile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nick552.healthysmile.domain.entity.VerificationToken;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByToken(String token);
}
