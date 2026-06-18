package com.dentalflow.auth.service.domain.services;

import com.dentalflow.auth.service.api.dtos.AuthResponseDto;
import com.dentalflow.auth.service.api.dtos.LoginRequestDto;
import com.dentalflow.auth.service.api.dtos.RegisterRequestDto;

public interface AuthService {
    void register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
