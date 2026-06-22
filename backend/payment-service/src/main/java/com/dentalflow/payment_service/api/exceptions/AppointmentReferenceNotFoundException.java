package com.dentalflow.payment_service.api.exceptions;

public class AppointmentReferenceNotFoundException extends RuntimeException {

    public AppointmentReferenceNotFoundException(Long id) {
        super("cita no encontrada, id: " + id);
    }
}
