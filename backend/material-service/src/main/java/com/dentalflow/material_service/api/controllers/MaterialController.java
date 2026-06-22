package com.dentalflow.material_service.api.controllers;

import com.dentalflow.material_service.api.dtos.MaterialRequestDto;
import com.dentalflow.material_service.api.dtos.MaterialResponseDto;
import com.dentalflow.material_service.domain.services.IMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {

    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponseDto create(@Valid @RequestBody MaterialRequestDto request) {
        return materialService.materialCreate(request);
    }

    @GetMapping
    public List<MaterialResponseDto> getAll() {
        return materialService.materialGetAll();
    }

    @GetMapping("/{id}")
    public MaterialResponseDto getById(@PathVariable Integer id) {
        return materialService.materialGetById(id);
    }

    @PutMapping("/{id}")
    public MaterialResponseDto update(@PathVariable Integer id, @Valid @RequestBody MaterialRequestDto request) {
        return materialService.materialUpdate(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        materialService.materialDelete(id);
    }

    @GetMapping("/stock-critico")
    public List<MaterialResponseDto> stockCritico() {
        return materialService.stockCritico();
    }

    /**
     * Lightweight endpoint consumed via Feign by dashboard-service, avoids
     * shipping the whole list just to display a counter on the dashboard.
     */
    @GetMapping("/stock-critico/count")
    public Map<String, Long> stockCriticoCount() {
        return Map.of("count", materialService.stockCriticoCount());
    }
}
