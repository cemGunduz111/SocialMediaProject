package com.cem.mapper;

import com.cem.dto.response.UserProfileResponseDto;
import com.cem.graphql.model.UserProfileInput;
import com.cem.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mapping(source = "id", target = "userid")
    UserProfile toUserProfile(final UserProfileResponseDto dto);

    List<UserProfileResponseDto> toUserProfileResponseDto(final List<UserProfile> userProfile);

    UserProfile toUserProfile(final UserProfileInput userProfileInput);
}
