package com.dentalflow.pe.data.repository;

import com.dentalflow.pe.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
