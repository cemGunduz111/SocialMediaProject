package com.cem.controller;

import com.cem.dto.request.ActivateRequestDto;
import com.cem.dto.request.NewCreateUserDto;
import com.cem.dto.request.UpdateRequestDto;
import com.cem.dto.response.RoleResponseDto;
import com.cem.dto.response.UserProfileRedisResponseDto;
import com.cem.dto.response.UserProfileResponseDto;
import com.cem.exception.ErrorType;
import com.cem.exception.UserServiceException;
import com.cem.mapper.IUserMapper;
import com.cem.repository.entity.UserProfile;
import com.cem.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.cem.constants.ApiUrls.*;
import static com.cem.constants.ApiUrls.GETALL;

import javax.validation.Valid;
import java.util.List;

/**
 * build gradle bağımlılıklarını ekleyelim
 * new user create dto yapalım
 * bir create metodu oluşturalım (NewUserCreateDto)
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto){
        try {
            userProfileService.createUser(dto);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            throw new UserServiceException(ErrorType.USER_NOT_CREATED);
        }
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){
        return ResponseEntity.ok(userProfileService.activateStatus(dto));
    }

    @PostMapping(ACTIVATESTATUSBYID)
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authid){

        return ResponseEntity.ok(userProfileService.activateStatus(authid));

    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateProfile(@RequestBody @Valid UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateUser(dto));
    }

    @GetMapping(GETALL)
    public ResponseEntity<List<UserProfileResponseDto>> getList (){
        return ResponseEntity.ok(IUserMapper.INSTANCE.toUserProfileResponseDto(userProfileService.findAll()));
    }

    @GetMapping("/findbyusername/{username}")
    public ResponseEntity<UserProfileRedisResponseDto> findbyUsername(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }

    @PutMapping("/updateredis")
    public ResponseEntity<Boolean> updateProfileForRedis(@RequestBody @Valid UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateUserForRedis(dto));
    }

    @GetMapping("findallactiveprofile")
    public ResponseEntity<List<UserProfile>> findAllActiveProfile(){
        return ResponseEntity.ok(userProfileService.findAllActiveProfile());
    }

    @GetMapping("/findbyrole")
    public ResponseEntity<List<RoleResponseDto>> findAllByRole(String roles){
        return ResponseEntity.ok(userProfileService.findByRole(roles));
    }

}
