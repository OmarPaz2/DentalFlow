package com.dentalflow.appointment_service.api.exceptions;

public class PatientReferenceNotFoundException extends RuntimeException {

    public PatientReferenceNotFoundException(Long patientId) {
        super("No existe un paciente con id: " + patientId);
    }
}
