package com.pascal.ecommerce.backend.repository;

import com.pascal.ecommerce.backend.model.FinalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinalOrderRepository extends JpaRepository<FinalOrder, Long> {
    List<FinalOrder> findAllByUserId(Long userId);
}
