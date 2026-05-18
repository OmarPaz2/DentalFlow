package com.dentalflow.pe.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientResponseDto {

    private Long id;
    private String dni;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;
}
