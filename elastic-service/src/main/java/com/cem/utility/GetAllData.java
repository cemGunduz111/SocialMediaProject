package com.cem.utility;

import com.cem.dto.response.UserProfileResponseDto;
import com.cem.manager.IUserManager;
import com.cem.mapper.IUserMapper;
import com.cem.repository.entity.UserProfile;
import com.cem.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllData {
    private final UserProfileService userProfileService;
    private final IUserManager userManager;

    //@PostConstruct
    public void init(){
        List<UserProfileResponseDto> userProfileList = userManager.getList().getBody();
        userProfileService.saveAll(userProfileList.stream().
                map(dto -> IUserMapper.INSTANCE.toUserProfile(dto))
                .collect(Collectors.toList()));
    }
}
