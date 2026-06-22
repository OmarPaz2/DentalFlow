package com.dentalflow.payment_service.api.exceptions;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Integer id) {
        super("Registro de pago no encontrado, id: " + id);
    }
}
