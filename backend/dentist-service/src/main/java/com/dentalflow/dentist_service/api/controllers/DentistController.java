package com.dentalflow.dentist_service.api.controllers;


import com.dentalflow.dentist_service.api.dtos.DentistRequestDto;
import com.dentalflow.dentist_service.api.dtos.DentistResponseDto;
import com.dentalflow.dentist_service.domain.services.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dentists")
public class DentistController {

    @Autowired
    IDentistService _dentistService;

    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<DentistResponseDto>> obtenerTodos(){
        return ResponseEntity.ok().body(_dentistService.obtenerTodosDentistas());
    }

    @PostMapping("/registrar")
    public void registrar(@RequestBody DentistRequestDto dto){
        _dentistService.registrarDentista(dto);
    }

}
