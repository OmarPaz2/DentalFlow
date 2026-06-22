package com.dentalflow.payment_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagos")
@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Id de treatment-service. Solo uno de treatmentId/appointmentId esta presente. Sin relacion JPA. */
    @Column(name = "treatment_id")
    private Integer treatmentId;

    /** Id de appointment-service. Sin relacion JPA. */
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    public enum MetodoPago {
        EFECTIVO, TARJETA, TRANSFERENCIA, YAPE_PLIN
    }
}
