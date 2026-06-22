package com.dentalflow.dentist_service.api.exceptions;

public class LicenseAlreadyExistsException extends RuntimeException {

    public LicenseAlreadyExistsException(String licenseNumber) {
        super("Ya existe personal clinico registrado con el numero de colegiatura: " + licenseNumber);
    }
}
