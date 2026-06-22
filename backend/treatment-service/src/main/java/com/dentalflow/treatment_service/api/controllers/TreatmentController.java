package com.dentalflow.treatment_service.api.controllers;

import com.dentalflow.treatment_service.api.dtos.TreatmentCostSummaryDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentRequestDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentResponseDto;
import com.dentalflow.treatment_service.domain.services.ITreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/treatments")
public class TreatmentController {

    private final ITreatmentService treatmentService;

    public TreatmentController(ITreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> registrar(@Valid @RequestBody TreatmentRequestDto request) {
        return Map.of("message", treatmentService.registrarTratamiento(request));
    }

    @GetMapping("/{id}")
    public TreatmentResponseDto getById(@PathVariable Integer id) {
        return treatmentService.getById(id);
    }

    @GetMapping("/patient-dni/{dni}")
    public TreatmentResponseDto getByPatientDni(@PathVariable String dni) {
        return treatmentService.getTratamiento(dni);
    }

    @PatchMapping("/{id}/estado")
    public Map<String, String> actualizarEstado(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        treatmentService.actualizarEstado(id, body.get("estado"));
        return Map.of("message", "estado actualizado correctamente");
    }

    /**
     * Consumido via Feign por payment-service antes de registrar un pago
     * asociado a un tratamiento (ver client/TreatmentClient en payment-service).
     */
    @GetMapping("/{id}/cost-summary")
    public TreatmentCostSummaryDto getCostSummary(@PathVariable Integer id) {
        return treatmentService.getCostSummary(id);
    }

    /**
     * Consumido via Feign por payment-service: indica si hay una sesion
     * PROGRAMADA esperando pago y cual es su costo parcial.
     */
    @GetMapping("/{id}/active-session")
    public ResponseEntity<com.dentalflow.treatment_service.api.dtos.ActiveSessionDto> getActiveSession(@PathVariable Integer id) {
        com.dentalflow.treatment_service.api.dtos.ActiveSessionDto activeSession = treatmentService.getActiveSession(id);
        return activeSession != null ? ResponseEntity.ok(activeSession) : ResponseEntity.noContent().build();
    }
}
