package com.dentalflow.treatment_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sesiones_tratamiento")
@Entity
public class TreatmentSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Id de TreatmentEntity en esta misma BD. Se mantiene la relacion JPA porque es intra-servicio. */
    @Column(name = "treatment_id", nullable = false)
    private Integer treatmentId;

    @Column(name = "fecha_programada")
    private LocalDateTime fechaProgramada;

    @Column(name = "fecha_realizada")
    private LocalDateTime fechaRealizada;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "costo_parcial", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal costoParcial = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoSesion estado = EstadoSesion.PROGRAMADA;

    public enum EstadoSesion {
        PROGRAMADA, REALIZADA, CANCELADA
    }
}
