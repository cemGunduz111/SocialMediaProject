package com.cem.manager;

import com.cem.dto.response.UserProfileResponseDto;
import com.cem.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.cem.constants.ApiUrls.GETALL;
@FeignClient(name = "user-profile-service", url = "${myapplication.feign.user}/user", decode404 = true)
public interface IUserManager {
    @GetMapping(GETALL)
    public ResponseEntity<List<UserProfileResponseDto>> getList ();
}
