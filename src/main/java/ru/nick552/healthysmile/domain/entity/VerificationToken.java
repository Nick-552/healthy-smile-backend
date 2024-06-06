package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Column(name = "expiry_datetime", nullable = false)
    private OffsetDateTime expiryDateTime;

    public VerificationToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expiryDateTime = calculateExpirationTime(EXPIRATION);
    }

    public static OffsetDateTime calculateExpirationTime(int minutesToExpire) {
        return OffsetDateTime.now().plusMinutes(minutesToExpire);
    }

    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(expiryDateTime);
    }
}
