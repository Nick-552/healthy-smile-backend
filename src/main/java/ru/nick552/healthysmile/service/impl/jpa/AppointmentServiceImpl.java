package ru.nick552.healthysmile.service.impl.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.nick552.healthysmile.domain.entity.AppointmentEntity;
import ru.nick552.healthysmile.domain.repository.AppointmentRepository;
import ru.nick552.healthysmile.domain.repository.DoctorRepository;
import ru.nick552.healthysmile.domain.repository.PatientRepository;
import ru.nick552.healthysmile.domain.repository.UserRepository;
import ru.nick552.healthysmile.dto.request.CreateAppointmentRequest;
import ru.nick552.healthysmile.dto.response.AppointmentDto;
import ru.nick552.healthysmile.exception.NoSuchAppointmentException;
import ru.nick552.healthysmile.exception.auth.NoRightsException;
import ru.nick552.healthysmile.exception.doctor.DoctorNotFoundException;
import ru.nick552.healthysmile.exception.user.UserNotFoundException;
import ru.nick552.healthysmile.model.Role;
import ru.nick552.healthysmile.service.AppointmentService;
import ru.nick552.healthysmile.util.UsernameExtractor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class AppointmentServiceImpl implements AppointmentService {

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void createAppointment(UUID doctorId, CreateAppointmentRequest request) {
        var username = UsernameExtractor.extractUsername();
        var user = userRepository.findByUsername(username).get();
        if (!(user.getRole() == Role.ADMIN
                || user.getRole() == Role.DOCTOR && user.getId().equals(doctorId)
        )) {
            throw new NoRightsException("Недостаточно прав для создания приема");
        }
        var doctor = doctorRepository.findById(doctorId)
                .orElseThrow(DoctorNotFoundException::new);
        for (int i = 0; i < request.count(); i++) {
            var appointment = new AppointmentEntity(
                    doctor,
                    request.startDateTime().plusMinutes(request.duration() * i),
                    request.duration()
            );
            doctor.addAppointment(appointment);
            appointmentRepository.save(appointment);
        }
    }

    @Override
    @Transactional
    public void setPatient(UUID id, UUID patientId) {
        var username = UsernameExtractor.extractUsername();
        var user = userRepository.findByUsername(username).get();
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!(user.getRole() == Role.ADMIN
                || user.getRole() == Role.DOCTOR && appointment.getDoctor().getId().equals(user.getId())
                || user.getRole() == Role.PATIENT && patientId.equals(user.getId())
        )) {
            throw new NoRightsException("Недостаточно прав для записи на прием");
        }
        if (!appointment.isOpen()) {
            throw new NoSuchAppointmentException();
        }
        var patient = patientRepository.findById(patientId)
                .orElseThrow(UserNotFoundException::new);
        patient.addAppointment(appointment);
    }

    @Override
    @Transactional
    public void removeAppointment(UUID id) {
        var username = UsernameExtractor.extractUsername();
        var user = userRepository.findByUsername(username).get();
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!(user.getRole() == Role.ADMIN
                || user.getRole() == Role.DOCTOR && user.getId().equals(appointment.getDoctor().getId())
        )) {
            throw new NoRightsException("Недостаточно прав для удаления приема");
        }
        appointmentRepository.delete(appointment);
    }

    @Override
    @Transactional
    public void cancelAppointment(UUID id) {
        var username = UsernameExtractor.extractUsername();
        var user = userRepository.findByUsername(username).get();
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchAppointmentException::new);
        if (!(user.getRole() == Role.ADMIN
                || user.getRole() == Role.PATIENT && user.getId().equals(appointment.getPatient().getId())
        )) {
            throw new NoRightsException("Недостаточно прав для удаления приема");
        }
        appointment.setPatient(null);
    }

    @Override
    public Set<AppointmentDto> getAppointments() {
        return appointmentRepository.findAll().stream().map(AppointmentEntity::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> getActiveAppointmentsForPatient(UUID patientId) {
        return getAllAppointmentsForPatient(patientId)
                .stream()
                .filter(AppointmentEntity::isActive)
                .map(AppointmentEntity::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> getCompletedAppointmentsForPatient(UUID patientId) {
        return getAllAppointmentsForPatient(patientId)
                .stream()
                .filter(AppointmentEntity::isCompleted)
                .map(AppointmentEntity::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> getOpenAppointmentsForDoctor(UUID doctorId) {
        return getAllAppointmentsForDoctor(doctorId)
                .stream()
                .filter(AppointmentEntity::isOpen)
                .map(AppointmentEntity::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> getCompletedAppointmentsForDoctor(UUID doctorId) {
        return getAllAppointmentsForDoctor(doctorId)
                .stream()
                .filter(AppointmentEntity::isCompleted)
                .map(AppointmentEntity::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AppointmentDto> getActiveAppointmentsForDoctor(UUID doctorId) {
        return getAllAppointmentsForDoctor(doctorId)
                .stream()
                .filter(AppointmentEntity::isActive)
                .map(AppointmentEntity::toDto)
                .collect(Collectors.toSet());
    }

    private Set<AppointmentEntity> getAllAppointmentsForDoctor(UUID doctorId) {
        var doctor = doctorRepository.findById(doctorId)
                .orElseThrow(DoctorNotFoundException::new);
        return doctor.getAppointmentEntities();
    }

    private Set<AppointmentEntity> getAllAppointmentsForPatient(UUID patientId) {
        var username = UsernameExtractor.extractUsername();
        var user = userRepository.findByUsername(username).get();
        if (!(user.getRole() == Role.ADMIN
                || user.getRole() == Role.DOCTOR
                || user.getRole() == Role.PATIENT && user.getId().equals(patientId)
        )) {
            throw new NoRightsException("Недостаточно прав для просмотра приемов");
        }
        var patient = patientRepository.findById(patientId)
                .orElseThrow(UserNotFoundException::new);
        return patient.getAppointmentEntities();
    }
}
