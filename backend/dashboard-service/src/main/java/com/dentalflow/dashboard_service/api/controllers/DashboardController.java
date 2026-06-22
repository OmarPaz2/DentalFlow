package com.dentalflow.dashboard_service.api.controllers;

import com.dentalflow.dashboard_service.api.dtos.DashboardMetricsResponseDto;
import com.dentalflow.dashboard_service.domain.services.IDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final IDashboardService dashboardService;

    public DashboardController(IDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    public DashboardMetricsResponseDto getMetrics() {
        return dashboardService.getMetrics();
    }
}
