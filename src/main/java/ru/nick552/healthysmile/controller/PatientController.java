package ru.nick552.healthysmile.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nick552.healthysmile.dto.response.AppointmentDto;
import ru.nick552.healthysmile.model.PatientInfo;
import ru.nick552.healthysmile.service.AppointmentService;
import ru.nick552.healthysmile.service.PatientService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @GetMapping("/{patient_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PatientInfo getPatientById(@PathVariable("patient_id") UUID patientId) {
        return patientService.getPatientById(patientId);
    }

    @GetMapping("/{patient_id}/appointments/active")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('DOCTOR')")
    public Set<AppointmentDto> getActiveAppointmentsForPatient(
            @PathVariable("patient_id") UUID patientId
    ) {
        return appointmentService.getActiveAppointmentsForPatient(patientId);
    }

    @GetMapping("/{patient_id}/appointments/completed")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('DOCTOR')")
    public Set<AppointmentDto> getCompletedAppointmentsForPatient(
            @PathVariable("patient_id") UUID patientId
    ) {
        return appointmentService.getCompletedAppointmentsForPatient(patientId);
    }
}
