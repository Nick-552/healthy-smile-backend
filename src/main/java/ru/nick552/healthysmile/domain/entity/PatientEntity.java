package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nick552.healthysmile.model.PatientInfo;

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

    public PatientInfo getPatientInfo() {
        return new PatientInfo(
                getUserInfo()
        );
    }
}
