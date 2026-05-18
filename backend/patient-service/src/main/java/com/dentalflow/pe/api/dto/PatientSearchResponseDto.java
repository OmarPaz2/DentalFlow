package com.dentalflow.pe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientSearchResponseDto {

    private String message;
    private List<PatientResponseDto> data;
}
