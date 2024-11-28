package com.pascal.ecommerce.backend.service;

import com.pascal.ecommerce.backend.enums.Role;
import com.pascal.ecommerce.backend.exceptions.AlreadyExistsException;
import com.pascal.ecommerce.backend.model.User;
import com.pascal.ecommerce.backend.repository.UserRepository;
import com.pascal.ecommerce.backend.request.LoginRequest;
import com.pascal.ecommerce.backend.request.RegisterRequest;
import com.pascal.ecommerce.backend.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = User.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .build();

                    userRepository.save(user);
                    return AuthResponse.builder().token(jwtService.getToken(user)).build();
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }
}
