package ru.nick552.healthysmile.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.nick552.healthysmile.dto.response.AppointmentDto;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @Column(name = "start_date_time")
    private OffsetDateTime startDateTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "conclusion")
    private String conclusion;

    public Boolean isFree() {
        return patient != null;
    }

    public Boolean isOpen() {
        return isFree() && !isExpired();
    }

    public Boolean isExpired() {
        return startDateTime.plusMinutes(duration).isBefore(OffsetDateTime.now());
    }

    public Boolean isActive() {
        return !isFree() && !isExpired();
    }

    public Boolean isCompleted() {
        return conclusion != null;
    }

    public AppointmentEntity(DoctorEntity doctor, OffsetDateTime startDateTime, Integer duration) {
        this.doctor = doctor;
        this.startDateTime = startDateTime;
        this.duration = duration;
    }

    public AppointmentDto toDto() {
        return new AppointmentDto(
                id,
                doctor.getId(),
                patient.getId(),
                startDateTime,
                duration
        );
    }
}
