package com.dentalflow.treatment_service.api.exceptions;

public class DentistReferenceNotFoundException extends RuntimeException {

    public DentistReferenceNotFoundException(Long id) {
        super("No existe personal clinico con id: " + id);
    }
}
