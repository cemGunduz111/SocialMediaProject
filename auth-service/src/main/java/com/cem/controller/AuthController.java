package com.cem.controller;

import com.cem.dto.request.ActivateRequestDto;
import com.cem.dto.request.LoginRequestDto;
import com.cem.dto.request.RegisterRequestDto;
import com.cem.dto.request.UpdateRequestDto;
import com.cem.dto.response.LoginResponseDto;
import com.cem.dto.response.RegisterResponseDto;
import com.cem.dto.response.RoleResponseDto;
import com.cem.repository.entity.Auth;
import com.cem.repository.enums.Roles;
import com.cem.service.AuthService;
import com.cem.utility.JwtTokenManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.cem.constants.ApiUrls.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;

    @GetMapping("/redis")
    public ResponseEntity<String> redisExample(String value){
        return ResponseEntity.ok(authService.redisExample(value));
    }

    @PostMapping( REGISTER)
    @Operation(summary = "Kullanıcı kayıt eden metot")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto).get());
    }


    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @GetMapping(GETALL)
    public ResponseEntity<List<Auth>> getList (){
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/findbyrole/{roles}")
    public ResponseEntity<List<RoleResponseDto>> findAllByRole(@PathVariable String roles){
        return ResponseEntity.ok(authService.findByRole(roles));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(authService.updateAuth(dto));
    }

}
