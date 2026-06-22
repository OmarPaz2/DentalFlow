package com.dentalflow.payment_service.api.controllers;

import com.dentalflow.payment_service.api.dtos.PaymentRequestDto;
import com.dentalflow.payment_service.api.dtos.PaymentResponseDto;
import com.dentalflow.payment_service.domain.services.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/treatment/{treatmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto registerPagoTratamiento(@PathVariable Integer treatmentId,
                                                        @Valid @RequestBody PaymentRequestDto request) {
        return paymentService.registerPagoTratamiento(request, treatmentId);
    }

    @PostMapping("/appointment/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto registerPagoCita(@PathVariable Long appointmentId,
                                                @Valid @RequestBody PaymentRequestDto request) {
        return paymentService.registerPagoCita(request, appointmentId);
    }

    @GetMapping("/{id}")
    public PaymentResponseDto findById(@PathVariable Integer id) {
        return paymentService.findPagoById(id);
    }

    /**
     * Consumido via Feign por treatment-service para mostrar "montoPagado"
     * sin que treatment-service necesite acceso directo a la BD de pagos.
     */
    @GetMapping("/treatment/{treatmentId}/total")
    public Map<String, BigDecimal> getTotalPaidForTreatment(@PathVariable Integer treatmentId) {
        return Map.of("total", paymentService.sumMontoByTreatmentId(treatmentId));
    }
}
