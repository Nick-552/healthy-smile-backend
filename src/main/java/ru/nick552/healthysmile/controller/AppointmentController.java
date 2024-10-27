package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nick552.healthysmile.dto.request.CreateAppointmentRequest;
import ru.nick552.healthysmile.dto.response.AppointmentDto;
import ru.nick552.healthysmile.service.AppointmentService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Log4j2
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<AppointmentDto> getAppointments() {
        log.info("Get all appointments");
        return appointmentService.getAppointments();
    }

    @PostMapping("/{doctor_id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void createAppointment(
            @RequestBody CreateAppointmentRequest createAppointmentRequest,
            @PathVariable("doctor_id") UUID doctorId
    ) {
        log.info("Create appointment {} for doctor {}", createAppointmentRequest, doctorId);
        appointmentService.createAppointment(doctorId, createAppointmentRequest);
    }

    @PostMapping("/{id}/{patient_id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('PATIENT')")
    public void updateAppointment(
            @PathVariable("id") UUID id,
            @PathVariable("patient_id") UUID patientId
    ) {
        log.info("Update appointment {} for patient {}", id, patientId);
        appointmentService.setPatient(id, patientId);
    }

    @DeleteMapping("/{id}/remove")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void removeAppointment(
            @PathVariable("id") UUID id
    ) {
        log.info("Remove appointment {}", id);
        appointmentService.removeAppointment(id);
    }

    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public void cancelAppointment(
            @PathVariable("id") UUID id
    ) {
        log.info("Cancel appointment {}", id);
        appointmentService.cancelAppointment(id);
    }
}
