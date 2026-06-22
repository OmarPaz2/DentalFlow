package com.dentalflow.specialty_service.api.exceptions;

public class SpecialtyNotFoundException extends RuntimeException {

    public SpecialtyNotFoundException(Long id) {
        super("No se encontro la especialidad con id: " + id);
    }
}
