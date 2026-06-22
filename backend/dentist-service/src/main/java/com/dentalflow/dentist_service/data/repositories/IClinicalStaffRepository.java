package com.dentalflow.dentist_service.data.repositories;

import com.dentalflow.dentist_service.data.entities.ClinicalStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClinicalStaffRepository extends JpaRepository<ClinicalStaffEntity, Long> {

    Optional<ClinicalStaffEntity> findByLicenseNumber(String licenseNumber);

    List<ClinicalStaffEntity> findByStaffType(ClinicalStaffEntity.StaffType staffType);
}
