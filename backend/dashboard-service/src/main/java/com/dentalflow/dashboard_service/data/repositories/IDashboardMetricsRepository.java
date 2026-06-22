package com.dentalflow.dashboard_service.data.repositories;

import com.dentalflow.dashboard_service.data.entities.DashboardMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDashboardMetricsRepository extends JpaRepository<DashboardMetricsEntity, Long> {
}
