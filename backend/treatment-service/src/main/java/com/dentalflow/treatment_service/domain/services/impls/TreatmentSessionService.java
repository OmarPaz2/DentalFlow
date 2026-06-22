package com.dentalflow.treatment_service.domain.services.impls;

import com.dentalflow.treatment_service.api.dtos.SessionRegisterRequestDto;
import com.dentalflow.treatment_service.api.dtos.SessionResponseDto;
import com.dentalflow.treatment_service.api.dtos.SessionUpdateRequestDto;
import com.dentalflow.treatment_service.api.exceptions.InvalidTreatmentException;
import com.dentalflow.treatment_service.api.exceptions.SessionNotFoundException;
import com.dentalflow.treatment_service.api.exceptions.TreatmentNotFoundException;
import com.dentalflow.treatment_service.client.PatientClient;
import com.dentalflow.treatment_service.client.dtos.PatientDto;
import com.dentalflow.treatment_service.data.entities.TreatmentEntity;
import com.dentalflow.treatment_service.data.entities.TreatmentEntity.EstadoTratamiento;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity.EstadoSesion;
import com.dentalflow.treatment_service.data.repositories.ITreatmentRepository;
import com.dentalflow.treatment_service.data.repositories.ITreatmentSessionRepository;
import com.dentalflow.treatment_service.domain.mappers.SessionMapper;
import com.dentalflow.treatment_service.domain.services.ITreatmentService;
import com.dentalflow.treatment_service.domain.services.ITreatmentSessionService;
import com.dentalflow.treatment_service.messaging.TreatmentCompletedEvent;
import com.dentalflow.treatment_service.messaging.TreatmentEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TreatmentSessionService implements ITreatmentSessionService {

    private final ITreatmentSessionRepository sessionRepository;
    private final ITreatmentRepository treatmentRepository;
    private final SessionMapper sessionMapper;
    private final ITreatmentService treatmentService;
    private final PatientClient patientClient;
    private final TreatmentEventPublisher eventPublisher;

    public TreatmentSessionService(ITreatmentSessionRepository sessionRepository,
                                    ITreatmentRepository treatmentRepository,
                                    SessionMapper sessionMapper,
                                    ITreatmentService treatmentService,
                                    PatientClient patientClient,
                                    TreatmentEventPublisher eventPublisher) {
        this.sessionRepository = sessionRepository;
        this.treatmentRepository = treatmentRepository;
        this.sessionMapper = sessionMapper;
        this.treatmentService = treatmentService;
        this.patientClient = patientClient;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public String registrarSesion(SessionRegisterRequestDto sesion) {
        TreatmentEntity tratamiento = treatmentRepository.findById(sesion.treatmentId())
                .orElseThrow(() -> new TreatmentNotFoundException(sesion.treatmentId()));

        TreatmentSessionEntity sesionProgramadaEntity = sessionRepository
                .findByTreatmentIdAndEstado(sesion.treatmentId(), EstadoSesion.PROGRAMADA);

        if (sesionProgramadaEntity != null) {
            throw new InvalidTreatmentException(
                    "No se puede crear una nueva sesion porque hay una sesion programada en curso");
        }

        if (sesion.costoParcial().compareTo(tratamiento.getCostoEstimado()) > 0) {
            throw new InvalidTreatmentException("El costo parcial no puede ser mayor al costo total del tratamiento");
        }

        List<TreatmentSessionEntity> listSesiones = sessionRepository.findAllByTreatmentId(sesion.treatmentId());

        BigDecimal totalCostoSesiones = BigDecimal.ZERO;
        int cantSesionesRealizadas = 0;
        for (TreatmentSessionEntity st : listSesiones) {
            if (st.getEstado() == EstadoSesion.REALIZADA) {
                totalCostoSesiones = totalCostoSesiones.add(st.getCostoParcial());
                cantSesionesRealizadas++;
            }
        }

        BigDecimal restoAPagarTratamiento = tratamiento.getCostoEstimado().subtract(totalCostoSesiones);

        if (sesion.costoParcial().compareTo(restoAPagarTratamiento) > 0) {
            throw new InvalidTreatmentException("el costo parcial no puede ser mayor al resto a pagar del tratamiento");
        }

        TreatmentSessionEntity sesionTratamiento = sessionMapper.toEntity(sesion);
        sesionTratamiento.setTreatmentId(tratamiento.getId());
        sesionTratamiento.setEstado(EstadoSesion.PROGRAMADA);

        int sesionesRestantes = tratamiento.getCantSesiones() - cantSesionesRealizadas;

        if (sesionesRestantes == 0) {
            throw new InvalidTreatmentException("Ya se han llevado a cabo todas las sesiones establecidas");
        }

        if (sesionesRestantes == 1) {
            if (sesion.costoParcial().compareTo(restoAPagarTratamiento) != 0) {
                sesionTratamiento.setCostoParcial(restoAPagarTratamiento);
                sessionRepository.save(sesionTratamiento);
                return "Sesion registrada correctamente, pero el monto parcial fue modificado al faltar 1 sesion";
            }
        }

        sessionRepository.save(sesionTratamiento);
        return "Sesion registrada correctamente";
    }

    @Override
    @Transactional
    public String actualizarSesion(SessionUpdateRequestDto sesion, Integer idSesion) {
        TreatmentSessionEntity sesionEntity = sessionRepository.findById(idSesion)
                .orElseThrow(() -> new SessionNotFoundException(idSesion));

        sesionEntity.setEstado(EstadoSesion.REALIZADA);
        sesionEntity.setFechaRealizada(sesion.fechaRealizada());
        sesionEntity.setObservaciones(sesion.observaciones());

        sessionRepository.save(sesionEntity);

        TreatmentEntity tratamiento = treatmentRepository.findById(sesionEntity.getTreatmentId())
                .orElseThrow(() -> new TreatmentNotFoundException(sesionEntity.getTreatmentId()));

        List<TreatmentSessionEntity> listSesiones = sessionRepository.findAllByTreatmentId(tratamiento.getId());
        int cantSesionesRealizadas = 0;
        for (TreatmentSessionEntity st : listSesiones) {
            if (st.getEstado() == EstadoSesion.REALIZADA) {
                cantSesionesRealizadas++;
            }
        }

        int sesionesRestantes = tratamiento.getCantSesiones() - cantSesionesRealizadas;

        if (sesionesRestantes == 0) {
            treatmentService.actualizarEstado(tratamiento.getId(), EstadoTratamiento.COMPLETADA.name());
            eventPublisher.publishTreatmentCompleted(
                    new TreatmentCompletedEvent(tratamiento.getId(), tratamiento.getPatientId()));
        } else if (tratamiento.getEstado() == EstadoTratamiento.PLANIFICADO) {
            treatmentService.actualizarEstado(tratamiento.getId(), EstadoTratamiento.EN_PROGRESO.name());
        }

        return "datos de la sesion registrada correctamente";
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDto getSesion(Integer sesionId) {
        TreatmentSessionEntity sesionEntity = sessionRepository.findById(sesionId)
                .orElseThrow(() -> new SessionNotFoundException(sesionId));

        TreatmentEntity tratamiento = treatmentRepository.findById(sesionEntity.getTreatmentId()).orElse(null);
        PatientDto paciente = tratamiento != null
                ? patientClient.getById(tratamiento.getPatientId().longValue())
                : null;

        return new SessionResponseDto(
                sesionEntity.getId(),
                sesionEntity.getTreatmentId(),
                paciente != null ? paciente.firstName() : null,
                paciente != null ? paciente.lastName() : null,
                paciente != null ? paciente.dni() : null,
                sesionEntity.getFechaProgramada(),
                sesionEntity.getFechaRealizada(),
                sesionEntity.getCostoParcial(),
                sesionEntity.getObservaciones(),
                sesionEntity.getEstado()
        );
    }

    @Override
    @Transactional
    public String cancelarSesion(Integer idSesion) {
        TreatmentSessionEntity sesionEntity = sessionRepository.findById(idSesion)
                .orElseThrow(() -> new SessionNotFoundException(idSesion));

        sesionEntity.setEstado(EstadoSesion.CANCELADA);
        sessionRepository.save(sesionEntity);
        return "sesion cancelada correctamente";
    }
}
