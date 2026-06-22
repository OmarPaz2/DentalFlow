package com.dentalflow.dentist_service.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clinical_staff")
@Entity
public class ClinicalStaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Id of the user/credentials record living in auth-service. Kept as a
     * plain numeric reference (no JPA relationship) since auth-service owns
     * its own database.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Id of the specialty record living in specialty-service. Only relevant
     * when staffType = ODONTOLOGO. Plain int reference, no FK constraint,
     * resolved at read time via Feign.
     */
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Column(name = "license_number", unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_type", nullable = false, length = 20)
    private StaffType staffType;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.available == null) {
            this.available = true;
        }
    }

    public enum StaffType {
        ADMINISTRADOR,
        RECEPCIONISTA,
        ODONTOLOGO
    }
}
