package com.dentalflow.auth.service.api.dtos;

import com.dentalflow.auth.service.data.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "Username is required")
        @Size(max = 50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8)
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
