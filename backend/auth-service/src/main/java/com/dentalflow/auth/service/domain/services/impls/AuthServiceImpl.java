package com.dentalflow.auth.service.domain.services.impls;

import com.dentalflow.auth.service.api.dtos.*;
import com.dentalflow.auth.service.data.entities.UserEntity;
import com.dentalflow.auth.service.data.repositories.UserRepository;
import com.dentalflow.auth.service.domain.mappers.UserMapper;
import com.dentalflow.auth.service.domain.services.AuthService;
import com.dentalflow.auth.service.domain.services.JwtService;
import com.dentalflow.auth.service.exceptions.InvalidCredentialsException;
import com.dentalflow.auth.service.exceptions.UserAlreadyExistsException;
import com.dentalflow.auth.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public void register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException(request.username());
        }

        UserEntity user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new UserNotFoundException(request.username())
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDto(token);
    }
}