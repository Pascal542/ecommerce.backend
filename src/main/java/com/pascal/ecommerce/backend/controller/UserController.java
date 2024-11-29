package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.dto.UserDto;
import com.pascal.ecommerce.backend.exceptions.AlreadyExistsException;
import com.pascal.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.pascal.ecommerce.backend.model.User;
import com.pascal.ecommerce.backend.request.CreateUserRequest;
import com.pascal.ecommerce.backend.request.LoginRequest;
import com.pascal.ecommerce.backend.request.UserUpdateRequest;
import com.pascal.ecommerce.backend.response.ApiResponse;
import com.pascal.ecommerce.backend.service.email.EmailService;
import com.pascal.ecommerce.backend.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final EmailService emailService;
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> convertedUsers = userService.convertUserToDtoList(users);
            return ResponseEntity.ok(new ApiResponse("Success", users));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);

            // send registration email
            emailService
                    .sendEmail(userDto.getEmail(),
                            "Registro de usuario",
                            "Usted ha registrado una cuenta en nuestro ecommerce.");


            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/add/admin")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createAdmin(request);
            UserDto userDto = userService.convertUserToDto(user);

            // send registration email
            emailService
                    .sendEmail(userDto.getEmail(),
                            "Registro de administrador",
                            "Usted ha registrado una cuenta en nuestro ecommerce.");


            return ResponseEntity.ok(new ApiResponse("Create Admin Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add/provider")
    public ResponseEntity<ApiResponse> createProvider(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createProvider(request);
            UserDto userDto = userService.convertUserToDto(user);

            // send registration email
            emailService
                    .sendEmail(userDto.getEmail(),
                            "Registro de proveedor",
                            "Usted ha registrado una cuenta en nuestro ecommerce.");


            return ResponseEntity.ok(new ApiResponse("Create Provider Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

















    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
