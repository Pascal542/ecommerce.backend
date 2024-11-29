package com.pascal.ecommerce.backend.service;

import com.pascal.ecommerce.backend.model.Role;
import com.pascal.ecommerce.backend.repository.RoleRepository;
import com.pascal.ecommerce.backend.request.RoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createRole(RoleRequest request) {

        Role role = new Role();
        role.setName(request.getName());
        return roleRepository.save(role);
    }
}
