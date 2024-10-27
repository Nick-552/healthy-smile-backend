package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nick552.healthysmile.model.Gender;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.model.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements UserDetails {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "^(\\+7|7|8)?\\s?\\(?[489][0-9]{2}\\)?\\s?[0-9]{3}\\s?[0-9]{2}\\s?[0-9]{2}$", message = "Phone should be valid")
    private String phone;

    @Length(min = 2, max = 25, message = "Name should be between 2 and 25 characters")
    @Column(name = "name")
    private String name;

    @Column
    @Length(min = 2, max = 25, message = "Surname should be between 2 and 25 characters")
    private String surname;

    @Column
    @Length(min = 2, max = 25, message = "Patronymic should be between 2 and 25 characters")
    private String patronymic;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(
                id,
                username,
                name,
                surname,
                patronymic,
                email,
                phone,
                role
        );
    }
}
