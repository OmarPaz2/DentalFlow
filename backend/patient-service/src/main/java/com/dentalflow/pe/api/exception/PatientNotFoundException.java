package com.dentalflow.pe.api.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("No se encontro un paciente con id: " + id);
    }
}
