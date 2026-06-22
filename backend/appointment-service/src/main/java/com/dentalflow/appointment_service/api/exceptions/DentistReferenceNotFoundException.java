package com.dentalflow.appointment_service.api.exceptions;

public class DentistReferenceNotFoundException extends RuntimeException {

    public DentistReferenceNotFoundException(Long dentistId) {
        super("No existe personal clinico con id: " + dentistId);
    }
}
