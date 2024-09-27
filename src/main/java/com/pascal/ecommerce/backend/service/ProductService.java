package com.pascal.ecommerce.backend.service;

import com.pascal.ecommerce.backend.enums.CategoryEnum;
import com.pascal.ecommerce.backend.models.Product;

import java.util.List;

public interface ProductService {

    Product addProductToCatalog(Product product);
    List<Product> getAllProductsInCatalog();
    Product getProductFromCatalogById(Integer id);
    List<Product> getProductByNameInCatalog(String ProductName);
    List<Product> getProductsByPriceRangeInCatalog(Double minPrice, Double maxPrice);
    List<Product> getProductsByCategoryInCatalog(CategoryEnum category);
}
