package com.pascal.ecommerce.backend.repository;

import com.pascal.ecommerce.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUserId(Long userId);
    Optional<CartItem> findByIdAndUserId(Long cartItemId, Long userId);
}
