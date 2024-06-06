package ru.nick552.healthysmile.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    USER(0),
    PATIENT(1),
    DOCTOR(2),
    ADMIN(3);

    private final int rank;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    public boolean isHigherOrEqual(Role role) {
        return rank > role.getRank();
    }
}
