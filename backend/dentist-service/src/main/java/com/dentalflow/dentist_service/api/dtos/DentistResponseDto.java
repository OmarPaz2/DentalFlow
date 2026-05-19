package com.dentalflow.dentist_service.api.dtos;

public record DentistResponseDto(
//        Long idDentista,
//        Long idUsuario,
        String nroLicencia,
        String nombre,
        String apellido,
        String telefono,
        String nombreEspecialidad
) {}