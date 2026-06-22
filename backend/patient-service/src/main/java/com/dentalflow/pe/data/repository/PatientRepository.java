package com.dentalflow.pe.data.repository;

import com.dentalflow.pe.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByDni(String dni);

    @Query("""
            SELECT p FROM Patient p WHERE
            (:dni IS NULL OR p.dni = :dni OR p.dni LIKE CONCAT(:dni, '%')) AND
            (:firstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND
            (:lastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
            """)
    List<Patient> search(
            @Param("dni") String dni,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );
}
