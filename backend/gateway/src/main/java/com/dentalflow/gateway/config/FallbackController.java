package com.dentalflow.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Generic fallback invoked by the CircuitBreaker gateway filter (see
 * application.yaml routes) whenever a downstream microservice is down or
 * times out, instead of letting the caller hang or get a raw connection
 * error.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> fallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                Map.of(
                        "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "error", "SERVICE_UNAVAILABLE",
                        "message", "El servicio solicitado no esta disponible en este momento. Intenta nuevamente en unos segundos.",
                        "timestamp", LocalDateTime.now().toString()
                )
        );
    }
}
