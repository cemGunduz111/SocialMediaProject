package com.cem.manager;

import com.cem.dto.request.UpdateRequestDto;
import com.cem.dto.response.RoleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auth-service", decode404 = true, url = "${myapplication.feign.auth}/auth")
public interface IAuthManager {
    @GetMapping("/findbyrole/{roles}")
    public ResponseEntity<List<RoleResponseDto>> findAllByRole(@PathVariable String roles);

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateRequestDto dto);
}
