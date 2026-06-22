package com.dentalflow.treatment_service.data.repositories;

import com.dentalflow.treatment_service.data.entities.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITreatmentRepository extends JpaRepository<TreatmentEntity, Integer> {

    Optional<TreatmentEntity> findFirstByPatientIdOrderByIdDesc(Integer patientId);

    List<TreatmentEntity> findByPatientId(Integer patientId);
}
