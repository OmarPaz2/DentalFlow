package com.dentalflow.dentist_service.data.repositories;

import com.dentalflow.dentist_service.data.entities.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDentistRepository extends JpaRepository<DentistEntity, Long> {
}
