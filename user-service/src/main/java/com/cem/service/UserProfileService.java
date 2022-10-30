package com.cem.service;

import com.cem.dto.request.ActivateRequestDto;
import com.cem.dto.request.NewCreateUserDto;
import com.cem.dto.request.UpdateRequestDto;
import com.cem.dto.response.RoleResponseDto;
import com.cem.dto.response.UserProfileRedisResponseDto;
import com.cem.dto.response.UserProfileResponseDto;
import com.cem.exception.ErrorType;
import com.cem.exception.UserServiceException;
import com.cem.manager.IAuthManager;
import com.cem.manager.IElasticManager;
import com.cem.mapper.IUserMapper;
import com.cem.repository.IUserProfileRepository;
import com.cem.repository.entity.UserProfile;
import com.cem.repository.enums.Status;
import com.cem.utility.JwtTokenManager;
import com.cem.utility.ServiceManager;
import org.apache.catalina.User;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository userProfileRepository;

    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;
    private final IAuthManager authManager;

    private final IElasticManager elasticManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager, CacheManager cacheManager,
                              IAuthManager authManager, IElasticManager elasticManager){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.cacheManager = cacheManager;
        this.authManager = authManager;
        this.elasticManager = elasticManager;
    }

    public UserProfile createUser(NewCreateUserDto dto){
        UserProfile userProfile = userProfileRepository.save(IUserMapper.INSTANCE.toUserProfile(dto));
        elasticManager.createUser(IUserMapper.INSTANCE.toUserProfileResponseDto(userProfile));
        return userProfile;
    }


    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthid(dto.getId());
        if (userProfile.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return true;
    }

    public Boolean updateUser(UpdateRequestDto dto) {

        Optional<Long> authid = jwtTokenManager.getUserId(dto.getToken());
        if (authid.isPresent()) {
            Optional<UserProfile> userProfileDb = userProfileRepository.findOptionalByAuthid(authid.get());
            if (userProfileDb.isPresent()){
                cacheManager.getCache("findbyusername").evict(userProfileDb.get().getUsername().toUpperCase());
                userProfileDb.get().setEmail(dto.getEmail());
                userProfileDb.get().setAddress(dto.getAddress());
                userProfileDb.get().setAbout(dto.getAbout());
                userProfileDb.get().setName(dto.getName());
                userProfileDb.get().setUsername(dto.getUsername());
                userProfileDb.get().setPhone(dto.getPhone());
                userProfileDb.get().setPhoto(dto.getPhoto());
                userProfileDb.get().setUpdated(System.currentTimeMillis());
                save(userProfileDb.get());
                elasticManager.update(IUserMapper.INSTANCE.toUserProfileResponseDto(userProfileDb.get()));
                authManager.updateAuth(dto);
                return true;
            }else {
                throw new UserServiceException(ErrorType.USER_NOT_FOUND);
            }
        }else {
            throw new UserServiceException(ErrorType.GECERSIZ_TOKEN);
        }
    }

    public Boolean activateStatus(Long authid) {

        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthid(authid);
        if (userProfile.isEmpty()){
            throw  new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return  true;
    }

    @Cacheable(value = "findbyusername", key = "#username.toUpperCase()")
    public UserProfileRedisResponseDto findByUsername(String username) {

        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUsernameContainingIgnoreCase(username);
        if (userProfile.isPresent())
            return IUserMapper.INSTANCE.toUserProfileRedisResponseDto(userProfile.get());
        else
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);

    }

    public Boolean updateUserForRedis(UpdateRequestDto dto) {

        Optional<Long> authid = jwtTokenManager.getUserId(dto.getToken());
        if (authid.isPresent()) {
            Optional<UserProfile> userProfileDb = userProfileRepository.findOptionalByAuthid(authid.get());
            if (userProfileDb.isPresent()){
                cacheManager.getCache("findbyusername").evict(userProfileDb.get().getUsername());
                userProfileDb.get().setEmail(dto.getEmail());
                userProfileDb.get().setAddress(dto.getAddress());
                userProfileDb.get().setAbout(dto.getAbout());
                userProfileDb.get().setName(dto.getName());
                userProfileDb.get().setUsername(dto.getUsername());
                userProfileDb.get().setPhone(dto.getPhone());
                userProfileDb.get().setPhoto(dto.getPhoto());
                save(userProfileDb.get());
                return true;
            }else {
                throw new UserServiceException(ErrorType.USER_NOT_FOUND);
            }
        }else {
            throw new UserServiceException(ErrorType.GECERSIZ_TOKEN);
        }
    }

    @Cacheable(value = "findactiveprofile")
    public List<UserProfile> findAllActiveProfile() {
        return userProfileRepository.getActiveProfile();
    }

    @Cacheable(value = "findbyrole", key = "#roles.toUpperCase()")
    public List<RoleResponseDto> findByRole(String roles) {
        return authManager.findAllByRole(roles).getBody();
    }
}
