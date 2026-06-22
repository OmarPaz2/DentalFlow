package com.dentalflow.payment_service.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * For payment-service, validation against treatment-service/appointment-service
 * is critical: we must never let a payment be recorded without successfully
 * verifying the amount against the source of truth. So unlike "soft"
 * fallbacks elsewhere, this one fails loudly with 503 instead of guessing.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DownstreamServiceUnavailableException extends RuntimeException {

    public DownstreamServiceUnavailableException(String serviceName) {
        super("No se pudo validar el pago porque '" + serviceName + "' no esta disponible en este momento. Intenta nuevamente en unos segundos.");
    }
}
