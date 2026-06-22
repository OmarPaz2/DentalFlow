package com.dentalflow.treatment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "payment-service", fallbackFactory = PaymentClientFallbackFactory.class)
public interface PaymentClient {

    /** Devuelve {"total": <suma de pagos del tratamiento>} */
    @GetMapping("/api/v1/payments/treatment/{treatmentId}/total")
    Map<String, BigDecimal> getTotalPaidForTreatment(@PathVariable("treatmentId") Integer treatmentId);
}
