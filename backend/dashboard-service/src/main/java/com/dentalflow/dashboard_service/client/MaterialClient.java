package com.dentalflow.dashboard_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "material-service", fallback = MaterialClientFallback.class)
public interface MaterialClient {

    @GetMapping("/api/v1/materials/stock-critico/count")
    Map<String, Long> getStockCriticoCount();
}
