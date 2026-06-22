package com.dentalflow.treatment_service.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DownstreamServiceUnavailableException extends RuntimeException {

    public DownstreamServiceUnavailableException(String serviceName) {
        super("El servicio '" + serviceName + "' no esta disponible en este momento. Intenta nuevamente en unos segundos.");
    }
}
