package com.dentalflow.treatment_service.api.controllers;

import com.dentalflow.treatment_service.api.dtos.SessionRegisterRequestDto;
import com.dentalflow.treatment_service.api.dtos.SessionResponseDto;
import com.dentalflow.treatment_service.api.dtos.SessionUpdateRequestDto;
import com.dentalflow.treatment_service.domain.services.ITreatmentSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/treatments/sessions")
public class TreatmentSessionController {

    private final ITreatmentSessionService sessionService;

    public TreatmentSessionController(ITreatmentSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> registrar(@Valid @RequestBody SessionRegisterRequestDto request) {
        return Map.of("message", sessionService.registrarSesion(request));
    }

    @PutMapping("/{id}")
    public Map<String, String> actualizar(@PathVariable Integer id, @RequestBody SessionUpdateRequestDto request) {
        return Map.of("message", sessionService.actualizarSesion(request, id));
    }

    @GetMapping("/{id}")
    public SessionResponseDto getById(@PathVariable Integer id) {
        return sessionService.getSesion(id);
    }

    @PutMapping("/{id}/cancelar")
    public Map<String, String> cancelar(@PathVariable Integer id) {
        return Map.of("message", sessionService.cancelarSesion(id));
    }
}
