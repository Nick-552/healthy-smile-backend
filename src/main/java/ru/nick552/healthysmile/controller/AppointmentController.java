package ru.nick552.healthysmile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nick552.healthysmile.dto.request.CreateAppointmentRequest;
import ru.nick552.healthysmile.service.AppointmentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/{doctor_id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void createAppointment(
            @RequestBody CreateAppointmentRequest createAppointmentRequest,
            @PathVariable("doctor_id") UUID doctorId
    ) {
        appointmentService.createAppointment(doctorId, createAppointmentRequest);
    }

    @PostMapping("/{id}/{patient_id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('PATIENT')")
    public void updateAppointment(
            @PathVariable("id") UUID id,
            @PathVariable("patient_id") UUID patientId
    ) {
        appointmentService.setPatient(id, patientId);
    }

    @DeleteMapping("/{id}/remove")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void removeAppointment(
            @PathVariable("id") UUID id
    ) {
        appointmentService.removeAppointment(id);
    }

    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public void cancelAppointment(
            @PathVariable("id") UUID id
    ) {
        appointmentService.cancelAppointment(id);
    }


}
