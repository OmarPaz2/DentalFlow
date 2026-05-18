package com.dentalflow.pe.api.controller;

import com.dentalflow.pe.api.dto.PatientRequestDto;
import com.dentalflow.pe.api.dto.PatientResponseDto;
import com.dentalflow.pe.api.dto.PatientSearchResponseDto;
import com.dentalflow.pe.domain.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDto create(@Valid @RequestBody PatientRequestDto request) {
        return patientService.create(request);
    }

    @GetMapping
    public PatientSearchResponseDto search(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        return patientService.search(dni, firstName, lastName);
    }
}
