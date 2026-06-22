package com.dentalflow.payment_service.api.exceptions;

public class TreatmentReferenceNotFoundException extends RuntimeException {

    public TreatmentReferenceNotFoundException(Integer id) {
        super("tratamiento no encontrado, id: " + id);
    }
}
