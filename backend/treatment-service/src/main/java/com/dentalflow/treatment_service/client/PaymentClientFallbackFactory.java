package com.dentalflow.treatment_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PaymentClientFallbackFactory implements FallbackFactory<PaymentClient> {

    private static final Logger log = LoggerFactory.getLogger(PaymentClientFallbackFactory.class);

    @Override
    public PaymentClient create(Throwable cause) {
        return treatmentId -> {
            log.warn("payment-service no disponible al consultar el total pagado del tratamiento {}: {}",
                    treatmentId, cause.getMessage());
            return Map.of("total", BigDecimal.ZERO);
        };
    }
}
