package com.dentalflow.specialty_service.data.repositories;

import com.dentalflow.specialty_service.data.entities.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISpecialtyRepository extends JpaRepository<SpecialtyEntity, Long> {

    Optional<SpecialtyEntity> findByName(String name);
}
