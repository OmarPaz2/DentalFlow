package com.dentalflow.dentist_service.api.exceptions;

public class ClinicalStaffNotFoundException extends RuntimeException {

    public ClinicalStaffNotFoundException(Long id) {
        super("No se encontro personal clinico con id: " + id);
    }
}
