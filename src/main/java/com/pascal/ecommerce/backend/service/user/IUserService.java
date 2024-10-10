package com.pascal.ecommerce.backend.service.user;

import com.pascal.ecommerce.backend.dto.UserDto;
import com.pascal.ecommerce.backend.model.User;
import com.pascal.ecommerce.backend.request.CreateUserRequest;
import com.pascal.ecommerce.backend.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
