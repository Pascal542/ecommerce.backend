package com.pascal.ecommerce.backend.repository;

import com.pascal.ecommerce.backend.model.Role;
import com.pascal.ecommerce.backend.request.RoleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
