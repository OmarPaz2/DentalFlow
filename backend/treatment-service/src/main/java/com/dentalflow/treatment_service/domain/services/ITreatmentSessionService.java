package com.dentalflow.treatment_service.domain.services;

import com.dentalflow.treatment_service.api.dtos.SessionRegisterRequestDto;
import com.dentalflow.treatment_service.api.dtos.SessionResponseDto;
import com.dentalflow.treatment_service.api.dtos.SessionUpdateRequestDto;

public interface ITreatmentSessionService {

    String registrarSesion(SessionRegisterRequestDto request);

    String actualizarSesion(SessionUpdateRequestDto request, Integer idSesion);

    SessionResponseDto getSesion(Integer sesionId);

    String cancelarSesion(Integer idSesion);
}
