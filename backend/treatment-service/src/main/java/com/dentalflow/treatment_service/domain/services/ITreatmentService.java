package com.dentalflow.treatment_service.domain.services;

import com.dentalflow.treatment_service.api.dtos.ActiveSessionDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentCostSummaryDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentRequestDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentResponseDto;

public interface ITreatmentService {

    String registrarTratamiento(TreatmentRequestDto request);

    TreatmentResponseDto getTratamiento(String dniPaciente);

    TreatmentResponseDto getById(Integer id);

    void actualizarEstado(Integer idTratamiento, String estado);

    TreatmentCostSummaryDto getCostSummary(Integer id);

    ActiveSessionDto getActiveSession(Integer treatmentId);
}
