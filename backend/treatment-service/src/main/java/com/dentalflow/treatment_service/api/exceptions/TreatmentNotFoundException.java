package com.dentalflow.treatment_service.api.exceptions;

public class TreatmentNotFoundException extends RuntimeException {

    public TreatmentNotFoundException(Object id) {
        super("Tratamiento no encontrado: " + id);
    }
}
