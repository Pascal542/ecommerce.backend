package com.pascal.ecommerce.backend.repository;

import com.pascal.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByFirstName(String username);
    Optional<User> findByEmail(String email);
}
