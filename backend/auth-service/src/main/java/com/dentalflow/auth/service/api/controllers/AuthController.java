package com.dentalflow.auth.service.api.controllers;

import com.dentalflow.auth.service.api.dtos.*;
import com.dentalflow.auth.service.domain.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public void register(
            @RequestBody @Valid RegisterRequestDto request
    ) {
        authService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public AuthResponseDto login(
            @RequestBody @Valid LoginRequestDto request
    ) {
        return authService.login(request);
    }
}