package com.pascal.ecommerce.backend.dto;

import com.pascal.ecommerce.backend.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCartItemDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
