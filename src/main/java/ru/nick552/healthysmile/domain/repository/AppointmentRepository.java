package ru.nick552.healthysmile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nick552.healthysmile.domain.entity.AppointmentEntity;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

}
