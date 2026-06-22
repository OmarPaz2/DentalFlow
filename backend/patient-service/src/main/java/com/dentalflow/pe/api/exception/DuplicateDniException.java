package com.dentalflow.pe.api.exception;

public class DuplicateDniException extends RuntimeException {

    public DuplicateDniException(String dni) {
        super("Ya existe un paciente registrado con el DNI: " + dni);
    }
}
