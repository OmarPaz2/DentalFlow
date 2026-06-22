package com.dentalflow.auth.service.domain.services;

import com.dentalflow.auth.service.data.entities.UserEntity;

public interface JwtService {
    String generateToken(UserEntity user);
}