package ru.nick552.healthysmile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nick552.healthysmile.domain.entity.DoctorEntity;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {

    @Modifying
    @Query(value = """
            INSERT INTO doctors
            (user_id, speciality, info, photo)
            VALUES (:#{#doctor.id}, :#{#doctor.speciality}, :#{#doctor.info}, :#{#doctor.photoLink})""",
            nativeQuery = true)
    void makeDoctor(@Param("doctor") DoctorEntity entity);

    List<DoctorEntity> findBySpecialityLikeIgnoreCase(String speciality);
}
