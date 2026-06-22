package com.dentalflow.appointment_service.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by Feign fallbacks when a downstream microservice could not be
 * reached at all (timeout, connection refused, circuit open) - as opposed
 * to a clean 404 from that service, which means the referenced
 * patient/dentist simply does not exist.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DownstreamServiceUnavailableException extends RuntimeException {

    public DownstreamServiceUnavailableException(String serviceName) {
        super("El servicio '" + serviceName + "' no esta disponible en este momento. Intenta nuevamente en unos segundos.");
    }
}
