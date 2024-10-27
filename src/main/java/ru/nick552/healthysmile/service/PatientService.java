package ru.nick552.healthysmile.service;

import ru.nick552.healthysmile.model.PatientInfo;

import java.util.UUID;

public interface PatientService {

    void createPatient(UUID userId);

    PatientInfo getPatientById(UUID userId);
}
