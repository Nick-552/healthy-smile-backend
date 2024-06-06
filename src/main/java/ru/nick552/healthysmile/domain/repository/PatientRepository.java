package ru.nick552.healthysmile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nick552.healthysmile.domain.entity.PatientEntity;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {

    @Modifying
    @Query(value = """
            INSERT INTO patients
            (user_id)
            VALUES (:#{#patient.id})""",
            nativeQuery = true)
    void makePatient(@Param("patient") PatientEntity entity);
}
