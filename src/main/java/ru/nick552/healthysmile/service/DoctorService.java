package ru.nick552.healthysmile.service;

import ru.nick552.healthysmile.domain.entity.DoctorEntity;
import ru.nick552.healthysmile.dto.request.AddDoctorRoleRequest;
import ru.nick552.healthysmile.dto.request.UpdateDoctorRequest;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    void addDoctor(AddDoctorRoleRequest request);
    void removeDoctor(UUID id);
    void updateDoctor(UpdateDoctorRequest request);
    DoctorEntity findDoctorById(UUID id);
    List<DoctorEntity> listAll();
    List<DoctorEntity> listBySpeciality(String speciality);

}
