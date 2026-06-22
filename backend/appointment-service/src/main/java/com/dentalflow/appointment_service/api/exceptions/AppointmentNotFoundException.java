package com.dentalflow.appointment_service.api.exceptions;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(Long id) {
        super("No se encontro la cita con id: " + id);
    }
}
