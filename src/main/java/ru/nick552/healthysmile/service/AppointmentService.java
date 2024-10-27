package ru.nick552.healthysmile.service;

import ru.nick552.healthysmile.dto.request.CreateAppointmentRequest;
import ru.nick552.healthysmile.dto.response.AppointmentDto;

import java.util.Set;
import java.util.UUID;

public interface AppointmentService {

    void createAppointment(UUID doctorId, CreateAppointmentRequest request);

    void setPatient(UUID id, UUID patientId);

    void removeAppointment(UUID id); // removes an appointment entity

    void cancelAppointment(UUID id); // cansel an appointment for patient

    Set<AppointmentDto> getAppointments();

    Set<AppointmentDto> getActiveAppointmentsForPatient(UUID patientId);

    Set<AppointmentDto> getCompletedAppointmentsForPatient(UUID patientId);

    Set<AppointmentDto> getOpenAppointmentsForDoctor(UUID doctorId);

    Set<AppointmentDto> getCompletedAppointmentsForDoctor(UUID doctorId);

    Set<AppointmentDto> getActiveAppointmentsForDoctor(UUID doctorId);
}
