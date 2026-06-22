package com.dentalflow.specialty_service.api.controllers;

import com.dentalflow.specialty_service.api.dtos.SpecialtyRequestDto;
import com.dentalflow.specialty_service.api.dtos.SpecialtyResponseDto;
import com.dentalflow.specialty_service.domain.services.ISpecialtyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {

    private final ISpecialtyService specialtyService;

    public SpecialtyController(ISpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialtyResponseDto create(@Valid @RequestBody SpecialtyRequestDto request) {
        return specialtyService.createSpecialty(request);
    }

    @PutMapping("/{id}")
    public SpecialtyResponseDto update(@PathVariable Long id, @Valid @RequestBody SpecialtyRequestDto request) {
        return specialtyService.updateSpecialty(id, request);
    }

    @GetMapping("/{id}")
    public SpecialtyResponseDto getById(@PathVariable Long id) {
        return specialtyService.getById(id);
    }

    @GetMapping
    public List<SpecialtyResponseDto> getAll() {
        return specialtyService.getAllSpecialties();
    }
}
