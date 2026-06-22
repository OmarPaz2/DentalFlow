package com.dentalflow.pe.domain.service.impl;

import com.dentalflow.pe.api.dto.PatientRequestDto;
import com.dentalflow.pe.api.dto.PatientResponseDto;
import com.dentalflow.pe.api.dto.PatientSearchResponseDto;
import com.dentalflow.pe.api.exception.DuplicateDniException;
import com.dentalflow.pe.api.exception.InvalidSearchCriteriaException;
import com.dentalflow.pe.api.exception.PatientNotFoundException;
import com.dentalflow.pe.data.entity.Patient;
import com.dentalflow.pe.data.repository.PatientRepository;
import com.dentalflow.pe.domain.mapper.PatientMapper;
import com.dentalflow.pe.domain.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final String NO_RESULTS_MESSAGE = "No se encontraron pacientes";
    private static final String RESULTS_MESSAGE = "Pacientes encontrados";

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDto create(PatientRequestDto request) {
        if (patientRepository.existsByDni(request.getDni())) {
            throw new DuplicateDniException(request.getDni());
        }

        Patient patient = patientMapper.toEntity(request);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDto getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientSearchResponseDto search(String dni, String firstName, String lastName) {
        String normalizedDni = normalize(dni);
        String normalizedFirstName = normalize(firstName);
        String normalizedLastName = normalize(lastName);

        if (normalizedDni == null && normalizedFirstName == null && normalizedLastName == null) {
            throw new InvalidSearchCriteriaException();
        }

        List<Patient> patients = patientRepository.search(
                normalizedDni,
                normalizedFirstName,
                normalizedLastName
        );

        List<PatientResponseDto> data = patients.stream()
                .map(patientMapper::toResponse)
                .toList();

        String message = data.isEmpty() ? NO_RESULTS_MESSAGE : RESULTS_MESSAGE;
        return new PatientSearchResponseDto(message, data);
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
