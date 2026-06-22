package com.dentalflow.dentist_service.api.controllers;

import com.dentalflow.dentist_service.api.dtos.ClinicalStaffRequestDto;
import com.dentalflow.dentist_service.api.dtos.ClinicalStaffResponseDto;
import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity.StaffType;
import com.dentalflow.dentist_service.domain.services.IClinicalStaffService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Base path kept as /api/v1/dentists (matching the original repo and the
 * Gateway route) even though this service now manages all clinical staff
 * (ADMINISTRADOR, RECEPCIONISTA, ODONTOLOGO), not just dentists.
 */
@RestController
@RequestMapping("/api/v1/dentists")
public class ClinicalStaffController {

    private final IClinicalStaffService clinicalStaffService;

    public ClinicalStaffController(IClinicalStaffService clinicalStaffService) {
        this.clinicalStaffService = clinicalStaffService;
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicalStaffResponseDto registrar(@Valid @RequestBody ClinicalStaffRequestDto request) {
        return clinicalStaffService.registrar(request);
    }

    @GetMapping("/obtenerTodos")
    public List<ClinicalStaffResponseDto> obtenerTodos() {
        return clinicalStaffService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ClinicalStaffResponseDto obtenerPorId(@PathVariable Long id) {
        return clinicalStaffService.obtenerPorId(id);
    }

    @GetMapping("/tipo/{staffType}")
    public List<ClinicalStaffResponseDto> obtenerPorTipo(@PathVariable StaffType staffType) {
        return clinicalStaffService.obtenerPorTipo(staffType);
    }

    @PatchMapping("/{id}/disponibilidad")
    public ClinicalStaffResponseDto actualizarDisponibilidad(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        boolean available = Boolean.TRUE.equals(body.get("available"));
        return clinicalStaffService.actualizarDisponibilidad(id, available);
    }
}
