package com.dentalflow.treatment_service.api.exceptions;

public class InvalidTreatmentException extends RuntimeException {

    public InvalidTreatmentException(String message) {
        super(message);
    }
}
