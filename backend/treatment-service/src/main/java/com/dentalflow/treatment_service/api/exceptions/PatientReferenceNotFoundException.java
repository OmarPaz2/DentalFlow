package com.dentalflow.treatment_service.api.exceptions;

public class PatientReferenceNotFoundException extends RuntimeException {

    public PatientReferenceNotFoundException(String detail) {
        super("Paciente no encontrado: " + detail);
    }
}
