package com.dentalflow.pe.domain.service;

import com.dentalflow.pe.api.dto.PatientRequestDto;
import com.dentalflow.pe.api.dto.PatientResponseDto;
import com.dentalflow.pe.api.dto.PatientSearchResponseDto;

public interface PatientService {

    PatientResponseDto create(PatientRequestDto request);

    PatientSearchResponseDto search(String dni, String firstName, String lastName);
}
