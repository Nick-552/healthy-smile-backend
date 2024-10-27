package ru.nick552.healthysmile.service.impl.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.DoctorEntity;
import ru.nick552.healthysmile.domain.repository.DoctorRepository;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.dto.request.AddDoctorRoleRequest;
import ru.nick552.healthysmile.dto.request.UpdateDoctorRequest;
import ru.nick552.healthysmile.exception.doctor.DoctorNotFoundException;
import ru.nick552.healthysmile.exception.user.UserNotFoundException;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.service.DoctorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addDoctor(AddDoctorRoleRequest request) {
        var user = userRepository
                .findById(request.userId())
                .orElseThrow(UserNotFoundException::new);
        if (!user.getRole().isHigherOrEqual(Role.DOCTOR)) {
            user.setRole(Role.DOCTOR);
        }
        var doctor = new DoctorEntity();
        doctor.setSpeciality(request.specialization());
        doctor.setInfo(request.info());
        doctor.setPhotoLink(request.photo());
        doctor.setId(request.userId());
        doctorRepository.makeDoctor(doctor);
    }

    @Override
    public void removeDoctor(UUID id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public void updateDoctor(UpdateDoctorRequest request) {
        var doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(DoctorNotFoundException::new);

        if (request.specialization() != null) {
            doctor.setSpeciality(request.specialization());
        }
        if (request.info() != null) {
            doctor.setInfo(request.info());
        }
        if (request.photo() != null) {
            doctor.setPhotoLink(request.photo());
        }
    }

    @Override
    public DoctorEntity findDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .orElseThrow(DoctorNotFoundException::new);
    }

    @Override
    public List<DoctorEntity> listAll() {
        return doctorRepository
                .findAll();
    }

    @Override
    public List<DoctorEntity> listBySpeciality(String speciality) {
        return doctorRepository
                .findBySpecialityLikeIgnoreCase(speciality);
    }
}
