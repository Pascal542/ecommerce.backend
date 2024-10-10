package com.pascal.ecommerce.backend.service.product;

import com.pascal.ecommerce.backend.dto.ProductDto;
import com.pascal.ecommerce.backend.model.Product;
import com.pascal.ecommerce.backend.request.AddProductRequest;
import com.pascal.ecommerce.backend.request.ProductUpdateRequest;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    // BASIC CRUD
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);

    // FILTERS
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    // DTO METHODS
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
