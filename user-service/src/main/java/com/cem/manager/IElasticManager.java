package com.cem.manager;


import com.cem.dto.response.UserProfileResponseDto;
import com.cem.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.cem.constants.ApiUrls.CREATE;
import static com.cem.constants.ApiUrls.UPDATE;

@FeignClient(name = "elastic-service", decode404 = true, url = "${myapplication.feign.elastic}/elastic")

public interface IElasticManager {
    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfileResponseDto UserProfileResponseDto);

    @PutMapping(UPDATE)
    public ResponseEntity<UserProfile> update(@RequestBody UserProfileResponseDto UserProfileResponseDto);
}
