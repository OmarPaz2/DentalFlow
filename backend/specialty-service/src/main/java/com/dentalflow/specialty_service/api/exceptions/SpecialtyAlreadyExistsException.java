package com.dentalflow.specialty_service.api.exceptions;

public class SpecialtyAlreadyExistsException extends RuntimeException {

    public SpecialtyAlreadyExistsException(String name) {
        super("Ya existe una especialidad con el nombre: " + name);
    }
}
