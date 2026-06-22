package com.dentalflow.appointment_service.data.repositories;

import com.dentalflow.appointment_service.data.entities.AppointmentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAppointmentTypeRepository extends JpaRepository<AppointmentTypeEntity, Long> {

    Optional<AppointmentTypeEntity> findByName(String name);
}
