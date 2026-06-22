package com.dentalflow.appointment_service.data.repositories;

import com.dentalflow.appointment_service.data.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByPatientId(Integer patientId);

    List<AppointmentEntity> findByDentistId(Integer dentistId);

    List<AppointmentEntity> findByDentistIdAndAppointmentDate(Integer dentistId, LocalDate appointmentDate);

    boolean existsByDentistIdAndAppointmentDateAndStartTimeAndStatusNot(
            Integer dentistId,
            LocalDate appointmentDate,
            java.time.LocalTime startTime,
            AppointmentEntity.AppointmentStatus status
    );
}
