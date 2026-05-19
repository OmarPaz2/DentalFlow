package com.dentalflow.dentist_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "specialties")
@Entity
public class SpecialtyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idEspecialidad;

    @Column(name = "name", nullable = false, length = 100)
    private String nombre;

}
