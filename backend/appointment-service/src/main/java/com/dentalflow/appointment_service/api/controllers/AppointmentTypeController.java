package com.dentalflow.appointment_service.api.controllers;

import com.dentalflow.appointment_service.api.dtos.AppointmentTypeRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentTypeResponseDto;
import com.dentalflow.appointment_service.domain.services.IAppointmentTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment-types")
public class AppointmentTypeController {

    private final IAppointmentTypeService service;

    public AppointmentTypeController(IAppointmentTypeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentTypeResponseDto create(@Valid @RequestBody AppointmentTypeRequestDto request) {
        return service.create(request);
    }

    @GetMapping
    public List<AppointmentTypeResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AppointmentTypeResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
