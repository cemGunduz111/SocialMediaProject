package com.cem.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/fallback-user-service")
    public ResponseEntity<String> userServiceFallback(){
        return ResponseEntity.ok("User service şu an hizmet vermiyor.");
    }
    @GetMapping("/fallback-auth-service")
    public ResponseEntity<String> authServiceFallback(){
        return ResponseEntity.ok("Auth service şu an hizmet vermiyor.");
    }
}
