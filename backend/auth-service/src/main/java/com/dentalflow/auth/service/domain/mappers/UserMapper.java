package com.dentalflow.auth.service.domain.mappers;

import com.dentalflow.auth.service.api.dtos.RegisterRequestDto;
import com.dentalflow.auth.service.data.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(RegisterRequestDto dto);
}
