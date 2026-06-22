package com.dentalflow.treatment_service.domain.services.impls;

import com.dentalflow.treatment_service.api.dtos.TreatmentCostSummaryDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentRequestDto;
import com.dentalflow.treatment_service.api.dtos.TreatmentResponseDto;
import com.dentalflow.treatment_service.api.exceptions.InvalidTreatmentException;
import com.dentalflow.treatment_service.api.exceptions.PatientReferenceNotFoundException;
import com.dentalflow.treatment_service.api.exceptions.TreatmentNotFoundException;
import com.dentalflow.treatment_service.client.DentistClient;
import com.dentalflow.treatment_service.client.PatientClient;
import com.dentalflow.treatment_service.client.PaymentClient;
import com.dentalflow.treatment_service.client.dtos.DentistDto;
import com.dentalflow.treatment_service.client.dtos.PatientDto;
import com.dentalflow.treatment_service.client.dtos.PatientSearchResponseDto;
import com.dentalflow.treatment_service.data.entities.TreatmentEntity;
import com.dentalflow.treatment_service.data.entities.TreatmentEntity.EstadoTratamiento;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity.EstadoSesion;
import com.dentalflow.treatment_service.data.repositories.ITreatmentRepository;
import com.dentalflow.treatment_service.data.repositories.ITreatmentSessionRepository;
import com.dentalflow.treatment_service.domain.mappers.TreatmentMapper;
import com.dentalflow.treatment_service.domain.services.ITreatmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TreatmentService implements ITreatmentService {

    private final ITreatmentRepository treatmentRepository;
    private final ITreatmentSessionRepository sessionRepository;
    private final TreatmentMapper mapper;
    private final PatientClient patientClient;
    private final DentistClient dentistClient;
    private final PaymentClient paymentClient;

    public TreatmentService(ITreatmentRepository treatmentRepository,
                             ITreatmentSessionRepository sessionRepository,
                             TreatmentMapper mapper,
                             PatientClient patientClient,
                             DentistClient dentistClient,
                             PaymentClient paymentClient) {
        this.treatmentRepository = treatmentRepository;
        this.sessionRepository = sessionRepository;
        this.mapper = mapper;
        this.patientClient = patientClient;
        this.dentistClient = dentistClient;
        this.paymentClient = paymentClient;
    }

    @Override
    @Transactional
    public String registrarTratamiento(TreatmentRequestDto request) {
        // Validaciones sincronas via Feign (protegidas por CircuitBreaker)
        PatientDto paciente = patientClient.getById(request.patientId());

        DentistDto odontologo = dentistClient.getById(request.dentistId());
        if (odontologo == null || !"ODONTOLOGO".equals(odontologo.staffType())) {
            throw new InvalidTreatmentException("El personal seleccionado no es un odontologo");
        }

        TreatmentEntity tratamiento = mapper.toEntity(request);
        tratamiento.setPatientId(paciente.id().intValue());
        tratamiento.setDentistId(odontologo.id().intValue());
        tratamiento.setEstado(EstadoTratamiento.PLANIFICADO);

        treatmentRepository.save(tratamiento);

        return "tratamiento registrado correctamente";
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentResponseDto getTratamiento(String dniPaciente) {
        PatientSearchResponseDto search = patientClient.searchByDni(dniPaciente);
        if (search.data() == null || search.data().isEmpty()) {
            throw new PatientReferenceNotFoundException("dni " + dniPaciente);
        }
        PatientDto paciente = search.data().get(0);

        TreatmentEntity tratamiento = treatmentRepository.findFirstByPatientIdOrderByIdDesc(paciente.id().intValue())
                .orElseThrow(() -> new TreatmentNotFoundException("paciente con dni " + dniPaciente));

        if (tratamiento.getEstado() == EstadoTratamiento.COMPLETADA) {
            throw new InvalidTreatmentException("El tratamiento ya fue completado");
        }

        return buildResponse(tratamiento, paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentResponseDto getById(Integer id) {
        TreatmentEntity tratamiento = treatmentRepository.findById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));
        PatientDto paciente = patientClient.getById(tratamiento.getPatientId().longValue());
        return buildResponse(tratamiento, paciente);
    }

    @Override
    @Transactional
    public void actualizarEstado(Integer idTratamiento, String estado) {
        TreatmentEntity tratamientoEntity = treatmentRepository.findById(idTratamiento)
                .orElseThrow(() -> new TreatmentNotFoundException(idTratamiento));

        tratamientoEntity.setEstado(EstadoTratamiento.valueOf(estado));
        treatmentRepository.save(tratamientoEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentCostSummaryDto getCostSummary(Integer id) {
        TreatmentEntity tratamiento = treatmentRepository.findById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));

        BigDecimal montoPagado = paymentClient.getTotalPaidForTreatment(id).getOrDefault("total", BigDecimal.ZERO);
        BigDecimal montoPendiente = tratamiento.getCostoEstimado().subtract(montoPagado);

        return new TreatmentCostSummaryDto(id, tratamiento.getCostoEstimado(), montoPagado, montoPendiente);
    }

    @Override
    @Transactional(readOnly = true)
    public com.dentalflow.treatment_service.api.dtos.ActiveSessionDto getActiveSession(Integer treatmentId) {
        TreatmentSessionEntity activa = sessionRepository.findByTreatmentIdAndEstado(treatmentId, EstadoSesion.PROGRAMADA);
        if (activa == null) {
            return null;
        }
        return new com.dentalflow.treatment_service.api.dtos.ActiveSessionDto(activa.getId(), activa.getCostoParcial());
    }

    private TreatmentResponseDto buildResponse(TreatmentEntity tratamiento, PatientDto paciente) {
        DentistDto odontologo = dentistClient.getById(tratamiento.getDentistId().longValue());

        int sesionesRealizadas = sessionRepository.countByTreatmentIdAndEstado(tratamiento.getId(), EstadoSesion.REALIZADA);
        int sesionesRestantes = tratamiento.getCantSesiones() - sesionesRealizadas;

        BigDecimal montoPagado = paymentClient.getTotalPaidForTreatment(tratamiento.getId())
                .getOrDefault("total", BigDecimal.ZERO);

        return new TreatmentResponseDto(
                tratamiento.getId(),
                tratamiento.getPatientId(),
                paciente != null ? paciente.firstName() : null,
                paciente != null ? paciente.lastName() : null,
                paciente != null ? paciente.dni() : null,
                tratamiento.getDentistId(),
                odontologo != null ? odontologo.firstName() : null,
                odontologo != null ? odontologo.lastName() : null,
                tratamiento.getDiagnostico(),
                tratamiento.getTipoTratamiento(),
                tratamiento.getCostoEstimado(),
                tratamiento.getFechaInicio(),
                tratamiento.getCantSesiones(),
                sesionesRestantes,
                montoPagado,
                tratamiento.getEstado()
        );
    }
}
