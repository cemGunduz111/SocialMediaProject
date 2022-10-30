package com.cem.mapper;

import com.cem.dto.request.NewCreateUserDto;
import com.cem.dto.request.UpdateRequestDto;
import com.cem.dto.response.UserProfileRedisResponseDto;
import com.cem.dto.response.UserProfileResponseDto;
import com.cem.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(final NewCreateUserDto dto);

    UserProfile toUserProfile(final UpdateRequestDto dto);

    UserProfileRedisResponseDto toUserProfileRedisResponseDto(final UserProfile userProfile);

    UserProfileResponseDto toUserProfileResponseDto(final UserProfile userProfile);
    List<UserProfileResponseDto> toUserProfileResponseDto(final List<UserProfile> userProfile);

}
