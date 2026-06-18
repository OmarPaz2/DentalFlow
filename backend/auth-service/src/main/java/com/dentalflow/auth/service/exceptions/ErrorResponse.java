package com.dentalflow.auth.service.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        int status,
        LocalDateTime timestamp,
        Map<String, String> errors

) {
}
