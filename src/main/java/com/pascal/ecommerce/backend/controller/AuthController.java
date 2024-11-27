package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.request.LoginRequest;
import com.pascal.ecommerce.backend.request.RegisterRequest;
import com.pascal.ecommerce.backend.response.AuthResponse;
import com.pascal.ecommerce.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/secured/login")
    public String securedLogin() {
        return "secured login from private endpoint (Admin)";
    }

    @PostMapping("/secured/register")
    public String securedRegister() {
        return "secured register from private endpoint (Admin)";
    }
}
