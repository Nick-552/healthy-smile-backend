package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@PrimaryKeyJoinColumn(name = "user_id")
public class PatientEntity extends UserEntity {

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AppointmentEntity> appointmentEntities;

    public void addAppointment(AppointmentEntity appointmentEntity) {
        appointmentEntities.add(appointmentEntity);
        appointmentEntity.setPatient(this);
    }
}
