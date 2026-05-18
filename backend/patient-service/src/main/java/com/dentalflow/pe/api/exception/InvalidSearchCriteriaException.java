package com.dentalflow.pe.api.exception;

public class InvalidSearchCriteriaException extends RuntimeException {

    public InvalidSearchCriteriaException() {
        super("Debe proporcionar al menos un criterio de búsqueda: dni, firstName o lastName");
    }
}
