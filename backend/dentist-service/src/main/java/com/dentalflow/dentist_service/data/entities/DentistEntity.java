package com.dentalflow.dentist_service.data.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dentists")
@Entity
public class DentistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idDentista;

    @Column(name = "user_id", nullable = false)
    private Long idUsuario;

    @Column(name = "license_number", nullable = false, columnDefinition = "VARCHAR(50)", unique = true)
    private String nroLicencia;

    @Column(name = "first_name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "last_name", nullable = false, length = 100)
    private String apellido;

    @Column(name = "phone", nullable = true, length = 20)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private SpecialtyEntity especialidad;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
