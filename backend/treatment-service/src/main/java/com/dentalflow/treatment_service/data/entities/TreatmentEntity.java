package com.dentalflow.treatment_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tratamientos")
@Entity
public class TreatmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Id de patient-service. Sin relacion JPA: solo un entero. */
    @Column(name = "patient_id", nullable = false)
    private Integer patientId;

    /** Id de dentist-service (odontologo). Sin relacion JPA: solo un entero. */
    @Column(name = "dentist_id", nullable = false)
    private Integer dentistId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "tipo_tratamiento", nullable = false, length = 100)
    private String tipoTratamiento;

    @Column(name = "costo_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "cant_sesiones", nullable = false)
    private Integer cantSesiones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoTratamiento estado = EstadoTratamiento.PLANIFICADO;

    public enum EstadoTratamiento {
        PLANIFICADO, EN_PROGRESO, COMPLETADA, INTERRUMPIDO
    }
}
