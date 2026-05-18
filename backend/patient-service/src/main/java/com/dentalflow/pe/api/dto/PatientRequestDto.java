package com.dentalflow.pe.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDto {
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @Pattern(regexp = "M|F", message = "El género debe ser M o F")
    private String gender;

    @NotBlank
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;
}
