package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nick552.healthysmile.model.DoctorInfo;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@PrimaryKeyJoinColumn(name = "user_id")
public class DoctorEntity extends UserEntity {

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "info")
    private String info;

    @Column(name = "photo")
    private String photoLink;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AppointmentEntity> appointmentEntities = new HashSet<>();

    public void addAppointment(AppointmentEntity appointmentEntity) {
        appointmentEntities.add(appointmentEntity);
        appointmentEntity.setDoctor(this);
    }

    public DoctorInfo getDoctorInfo() {
        return new DoctorInfo(
                getUserInfo(),
                speciality,
                URI.create(photoLink),
                info
        );
    }

    public boolean hasSpeciality(String speciality) {
        return this.speciality.contains(speciality);
    }
}
