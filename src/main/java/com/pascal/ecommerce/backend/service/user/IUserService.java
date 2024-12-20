package com.pascal.ecommerce.backend.service.user;

import com.pascal.ecommerce.backend.dto.UserDto;
import com.pascal.ecommerce.backend.model.User;
import com.pascal.ecommerce.backend.request.CreateUserRequest;
import com.pascal.ecommerce.backend.request.LoginRequest;
import com.pascal.ecommerce.backend.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    List<UserDto> convertUserToDtoList(List<User> users);
    User login(LoginRequest request);
    User createUser(CreateUserRequest request);
    User createAdmin(CreateUserRequest request);
    User createProvider(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
