package com.cem.mapper;

import com.cem.dto.request.ActivateRequestDto;
import com.cem.dto.request.LoginRequestDto;
import com.cem.dto.request.NewCreateUserDto;
import com.cem.dto.request.RegisterRequestDto;
import com.cem.dto.response.LoginResponseDto;
import com.cem.dto.response.RegisterResponseDto;
import com.cem.dto.response.RoleResponseDto;
import com.cem.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    RegisterRequestDto toRegisterRequestDto(final Auth auth);

    Auth toAuth (final RegisterRequestDto requestDto);

    LoginRequestDto toLoginRequestDto(final Auth auth);

    Auth toAuth (final LoginRequestDto requestDto);

    LoginResponseDto toLoginResponseDto(final Auth auth);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);

    NewCreateUserDto toNewCreateUserDto(final Auth auth);
    RoleResponseDto toRoleResponseDto(final Auth auth);
}
