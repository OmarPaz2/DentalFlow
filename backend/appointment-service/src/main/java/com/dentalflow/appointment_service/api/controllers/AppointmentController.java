package com.dentalflow.appointment_service.api.controllers;

import com.dentalflow.appointment_service.api.dtos.AppointmentRequestDto;
import com.dentalflow.appointment_service.api.dtos.AppointmentResponseDto;
import com.dentalflow.appointment_service.api.dtos.RescheduleRequestDto;
import com.dentalflow.appointment_service.domain.services.IAppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto create(@Valid @RequestBody AppointmentRequestDto request) {
        return appointmentService.create(request);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDto> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getByPatient(patientId);
    }

    @GetMapping("/dentist/{dentistId}")
    public List<AppointmentResponseDto> getByDentist(@PathVariable Long dentistId) {
        return appointmentService.getByDentist(dentistId);
    }

    @PutMapping("/{id}/reschedule")
    public AppointmentResponseDto reschedule(@PathVariable Long id, @Valid @RequestBody RescheduleRequestDto request) {
        return appointmentService.reschedule(id, request);
    }

    @PutMapping("/{id}/cancel")
    public AppointmentResponseDto cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }
}
