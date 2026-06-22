package com.dentalflow.treatment_service.data.repositories;

import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity;
import com.dentalflow.treatment_service.data.entities.TreatmentSessionEntity.EstadoSesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITreatmentSessionRepository extends JpaRepository<TreatmentSessionEntity, Integer> {

    int countByTreatmentIdAndEstado(Integer treatmentId, EstadoSesion estado);

    TreatmentSessionEntity findByTreatmentIdAndEstado(Integer treatmentId, EstadoSesion estado);

    List<TreatmentSessionEntity> findAllByTreatmentId(Integer treatmentId);
}
