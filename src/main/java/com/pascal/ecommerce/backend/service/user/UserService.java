package com.pascal.ecommerce.backend.service.user;

import com.pascal.ecommerce.backend.dto.UserDto;
import com.pascal.ecommerce.backend.exceptions.AlreadyExistsException;
import com.pascal.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.pascal.ecommerce.backend.model.Role;
import com.pascal.ecommerce.backend.model.User;
import com.pascal.ecommerce.backend.repository.RoleRepository;
import com.pascal.ecommerce.backend.repository.UserRepository;
import com.pascal.ecommerce.backend.request.CreateUserRequest;
import com.pascal.ecommerce.backend.request.LoginRequest;
import com.pascal.ecommerce.backend.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User login(LoginRequest request) {
        return userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    Role userRole = roleRepository.findByName("USER")
                            .orElseThrow(() -> new ResourceNotFoundException("Role USER not found!"));

                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEnabled(true);
                    user.setRole(List.of(userRole));
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }
    @Override
    public User createAdmin(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    Role userRole = roleRepository.findByName("ADMIN")
                            .orElseThrow(() -> new ResourceNotFoundException("Role ADMIN not found!"));

                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEnabled(true);
                    user.setRole(List.of(userRole));
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User createProvider(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    Role userRole = roleRepository.findByName("PROVIDER")
                            .orElseThrow(() -> new ResourceNotFoundException("Role ADMIN not found!"));

                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEnabled(true);
                    user.setRole(List.of(userRole));
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }












    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return  userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw new ResourceNotFoundException("User not found!");
        });
        //  Update de estado
    }

    @Override
    public UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class); // Convierte el objeto base
        List<String> roleNames = user.getRole().stream()       // Extrae los nombres de los roles
                .map(Role::getName)
                .toList();
        userDto.setRoles(roleNames);                          // Asigna los roles al DTO
        return userDto;
    }

    @Override
    public List<UserDto> convertUserToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertUserToDto) // Convertir cada usuario a UserDto
                .collect(Collectors.toList()); // Usar Collectors.toList() para recopilar los resultados
    }





}
