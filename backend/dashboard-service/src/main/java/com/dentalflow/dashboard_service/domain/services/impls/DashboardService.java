package com.dentalflow.dashboard_service.domain.services.impls;

import com.dentalflow.dashboard_service.api.dtos.DashboardMetricsResponseDto;
import com.dentalflow.dashboard_service.client.MaterialClient;
import com.dentalflow.dashboard_service.data.entities.DashboardMetricsEntity;
import com.dentalflow.dashboard_service.data.repositories.IDashboardMetricsRepository;
import com.dentalflow.dashboard_service.domain.services.IDashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DashboardService implements IDashboardService {

    private static final Long METRICS_ROW_ID = 1L;

    private final IDashboardMetricsRepository repository;
    private final MaterialClient materialClient;

    public DashboardService(IDashboardMetricsRepository repository, MaterialClient materialClient) {
        this.repository = repository;
        this.materialClient = materialClient;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardMetricsResponseDto getMetrics() {
        DashboardMetricsEntity metrics = getOrCreate();

        // Llamada en vivo via Feign, protegida por CircuitBreaker (ver application.yaml).
        // Si material-service esta caido, el fallback devuelve -1 y usamos el ultimo
        // valor cacheado en BD en su lugar, para que el dashboard nunca se rompa.
        long stockCritico = materialClient.getStockCriticoCount().getOrDefault("count", -1L);
        if (stockCritico < 0) {
            stockCritico = metrics.getMaterialesStockCriticoCache();
        } else if (stockCritico != metrics.getMaterialesStockCriticoCache()) {
            metrics.setMaterialesStockCriticoCache((int) stockCritico);
            repository.save(metrics);
        }

        return new DashboardMetricsResponseDto(
                metrics.getCitasHoy(),
                metrics.getTratamientosCompletados(),
                metrics.getPagosRealizados(),
                metrics.getMontoTotalPagos(),
                (int) stockCritico,
                metrics.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public void incrementCitasHoy() {
        DashboardMetricsEntity metrics = getOrCreate();
        metrics.setCitasHoy(metrics.getCitasHoy() + 1);
        repository.save(metrics);
    }

    @Override
    @Transactional
    public void incrementTratamientosCompletados() {
        DashboardMetricsEntity metrics = getOrCreate();
        metrics.setTratamientosCompletados(metrics.getTratamientosCompletados() + 1);
        repository.save(metrics);
    }

    @Override
    @Transactional
    public void registrarPago(BigDecimal monto) {
        DashboardMetricsEntity metrics = getOrCreate();
        metrics.setPagosRealizados(metrics.getPagosRealizados() + 1);
        metrics.setMontoTotalPagos(metrics.getMontoTotalPagos().add(monto));
        repository.save(metrics);
    }

    private DashboardMetricsEntity getOrCreate() {
        return repository.findById(METRICS_ROW_ID)
                .orElseGet(() -> repository.save(
                        DashboardMetricsEntity.builder().id(METRICS_ROW_ID).build()
                ));
    }
}
