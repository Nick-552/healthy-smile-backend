package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nick552.healthysmile.domain.entity.DoctorEntity;
import ru.nick552.healthysmile.dto.request.CreateAppointmentRequest;
import ru.nick552.healthysmile.dto.response.AppointmentDto;
import ru.nick552.healthysmile.model.DoctorInfo;
import ru.nick552.healthysmile.service.AppointmentService;
import ru.nick552.healthysmile.service.DoctorService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    private final DoctorService doctorService;

    private final AppointmentService appointmentService;

    @GetMapping
    public List<DoctorInfo> getAll() {
        return doctorService.listAll()
                .stream()
                .map(DoctorEntity::getInfo)
                .toList();
    }

    @GetMapping("/filter/")
    public List<DoctorInfo> getFiltered(@RequestParam String speciality) {
        return doctorService.listBySpeciality(speciality)
                .stream()
                .map(DoctorEntity::getInfo)
                .toList();
    }

    @GetMapping("/{id}")
    public DoctorInfo getById(@PathVariable UUID id) {
        return doctorService.findDoctorById(id)
                .getInfo();
    }

    @PostMapping("/{doctor_id}/appointments")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void createAppointment(
            @RequestBody CreateAppointmentRequest createAppointmentRequest,
            @PathVariable("doctor_id") UUID doctorId
    ) {
        appointmentService.createAppointment(doctorId, createAppointmentRequest);
    }

    @GetMapping("/{doctor_id}/appointments/active")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public Set<AppointmentDto> getActiveAppointmentsForDoctor(@PathVariable("doctor_id") UUID doctorId) {
        return appointmentService.getActiveAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/{doctor_id}/appointments/completed")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public Set<AppointmentDto> getCompletedAppointmentsForDoctor(@PathVariable("doctor_id") UUID doctorId) {
        return appointmentService.getCompletedAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/{doctor_id}/appointments/open")
    public Set<AppointmentDto> getOpenAppointmentsForDoctor(@PathVariable("doctor_id") UUID doctorId) {
        return appointmentService.getOpenAppointmentsForDoctor(doctorId);
    }
}
