package com.pascal.ecommerce.backend.repository;

import com.pascal.ecommerce.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
