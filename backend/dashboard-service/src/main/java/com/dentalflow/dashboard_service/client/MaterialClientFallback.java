package com.dentalflow.dashboard_service.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MaterialClientFallback implements MaterialClient {

    @Override
    public Map<String, Long> getStockCriticoCount() {
        return Map.of("count", -1L);
    }
}
