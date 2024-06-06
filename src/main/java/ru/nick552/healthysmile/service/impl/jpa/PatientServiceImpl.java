package ru.nick552.healthysmile.service.impl.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.PatientEntity;
import ru.nick552.healthysmile.domain.repository.PatientRepository;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.service.PatientService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createPatient(UUID userId) {
        var user = userRepository.findById(userId).orElseThrow();
        var patient = new PatientEntity();
        patient.setId(user.getId());
        if (!user.getRole().isHigherOrEqual(Role.PATIENT)) {
            user.setRole(Role.PATIENT);
        }
        patientRepository.save(patient);
    }
}
