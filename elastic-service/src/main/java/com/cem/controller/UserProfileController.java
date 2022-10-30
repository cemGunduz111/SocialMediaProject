package com.cem.controller;

import com.cem.dto.response.UserProfileResponseDto;
import com.cem.mapper.IUserMapper;
import com.cem.repository.entity.UserProfile;
import com.cem.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cem.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ELASTIC)
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping(GETALL)
    public ResponseEntity<Iterable<UserProfile>> getAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfileResponseDto UserProfileResponseDto){
        return ResponseEntity.ok(userProfileService.save(IUserMapper.INSTANCE.toUserProfile(UserProfileResponseDto)));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<UserProfile> update(@RequestBody UserProfileResponseDto UserProfileResponseDto){
        return ResponseEntity.ok(userProfileService.update(IUserMapper.INSTANCE.toUserProfile(UserProfileResponseDto)));
    }
}
