package com.dentalflow.appointment_service.api.exceptions;

public class AppointmentTypeNotFoundException extends RuntimeException {

    public AppointmentTypeNotFoundException(Long id) {
        super("No se encontro el tipo de cita con id: " + id);
    }
}
