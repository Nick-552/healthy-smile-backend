package ru.nick552.healthysmile.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.VerificationToken;
import ru.nick552.healthysmile.domain.repository.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
public class VerificationTokenExpirationScheduler {

    private final VerificationTokenRepository verificationTokenRepository;


    @Scheduled(cron = "0 0 0 * * *")
    public void removeExpiredTokens() {
        var tokens = verificationTokenRepository.findAll()
                .stream()
                .filter(VerificationToken::isExpired)
                .toList();
        verificationTokenRepository.deleteAllInBatch(tokens);
    }
}
