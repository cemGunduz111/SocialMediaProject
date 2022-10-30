package com.cem.service;

import com.cem.dto.request.*;
import com.cem.dto.response.LoginResponseDto;
import com.cem.dto.response.RegisterResponseDto;
import com.cem.dto.response.RoleResponseDto;
import com.cem.exception.AuthServiceException;
import com.cem.exception.ErrorType;
import com.cem.manager.IUserManager;
import com.cem.mapper.IAuthMapper;
import com.cem.repository.IAuthRepository;
import com.cem.repository.entity.Auth;
import com.cem.repository.enums.Roles;
import com.cem.repository.enums.Status;
import com.cem.utility.CodeGenerator;
import com.cem.utility.JwtTokenManager;
import com.cem.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 1- activeterequestdto -> id, activate code
 *    boolean dönüştü bir end point ( activate request dto )
 *    serviceden gelen dto dan kontroller yapacağız
 *    databasede bu idli kullanıcı var mı
 *    varsa gönderdiğimiz code ile databasedeki kod aynı mı
 */

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;

    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;
    // private final CodeGenerator codeGenerator;
    public AuthService(IAuthRepository authRepository,
                       IUserManager userManager,
                       JwtTokenManager jwtTokenManager,
                       CacheManager cacheManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
        this.cacheManager = cacheManager;

        // this.codeGenerator = codeGenerator;
    }

    @Cacheable(value = "redis_example")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
        }catch (Exception e){
        }
        return value;
    }

//    @Cacheable(value = "hello")
//    public String hello(){
//        try {
//            Thread.sleep(3000);
//        } catch (Exception e){
//
//        }
//        return "merhaba";
//    }

    public Optional<LoginResponseDto> login(LoginRequestDto dto){
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isPresent()){
            LoginResponseDto loginResponseDto = IAuthMapper.INSTANCE.toLoginResponseDto(auth.get());
            String token = jwtTokenManager.createToken(loginResponseDto.getId());
            loginResponseDto.setToken(token);

            return Optional.of(loginResponseDto);
        } else {
            throw new AuthServiceException(ErrorType.LOGIN_ERROR_WRONG);
        }
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);

//        if (userIsExist(dto.getUsername())){
//            throw new AuthServiceException(ErrorType.USERNAME_DUPLICATE);
//        }else {
//            if (dto.getAdmincode() != null && dto.getAdmincode().equals("admin"))
//                auth.setRoles(Roles.ADMIN);
//            try {
//                return save(auth);
//            }catch (Exception e){
//                throw new AuthServiceException(ErrorType.USER_NOT_CREATED);
//            }
//
//        }


        if (dto.getAdmincode() != null && dto.getAdmincode().equals("admin"))
            auth.setRoles(Roles.ADMIN);

        try {
            auth.setActivatedCode(CodeGenerator.generateCode(UUID.randomUUID().toString()));
            save(auth);
            cacheManager.getCache("findbyrole").evict(auth.getRoles());
            userManager.createUser(NewCreateUserDto.builder()
                    .authid(auth.getId())
                    .email(auth.getEmail())
                    .username(auth.getUsername())
                    .build());
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        }catch (Exception e){
            throw new AuthServiceException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean userIsExist(String username){
        return authRepository.existUserName(username);
    }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if (auth.isEmpty())
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if (auth.get().getActivatedCode().equals(dto.getActivatedCode())) {
            auth.get().setStatus(Status.ACTIVE);
            userManager.activateStatus(dto.getId());
            save(auth.get());
            cacheManager.getCache("findactiveprofile").clear();
            return true;
        }
        throw new AuthServiceException(ErrorType.INVALID_ACTIVATE_CODE);
    }

    public List<RoleResponseDto> findByRole(String roles){
        Roles roles1;
        try {
            roles1 = Roles.valueOf(roles.toUpperCase());
        }catch (Exception e){
            throw new AuthServiceException(ErrorType.ROLE_NOT_FOUND);
        }

        return authRepository.findAllByRoles(roles1).stream().
                map(x->
                        IAuthMapper.INSTANCE.toRoleResponseDto(x))
                .collect(Collectors.toList());
    }


    public Boolean updateAuth(UpdateRequestDto dto) {

        Optional<Long> authid = jwtTokenManager.getUserId(dto.getToken());
        if (authid.isPresent()) {
            Optional<Auth> authDb = authRepository.findById(authid.get());
            if (authDb.isPresent()){
                authDb.get().setEmail(dto.getEmail());
                authDb.get().setUsername(dto.getUsername());
                save(authDb.get());
                return true;
            }else {
                throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
            }
        }else {
            throw new AuthServiceException(ErrorType.GECERSIZ_TOKEN);
        }

    }
}
