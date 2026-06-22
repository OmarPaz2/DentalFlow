package com.dentalflow.treatment_service.api.exceptions;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(Integer id) {
        super("Sesion de tratamiento no encontrada, id: " + id);
    }
}
