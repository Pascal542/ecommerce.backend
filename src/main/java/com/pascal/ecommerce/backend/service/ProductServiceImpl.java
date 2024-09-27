package com.pascal.ecommerce.backend.service;

import com.pascal.ecommerce.backend.enums.CategoryEnum;
import com.pascal.ecommerce.backend.models.Product;
import com.pascal.ecommerce.backend.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product addProductToCatalog(Product product) {
        return productDao.save(product);
    }

    @Override
    public List<Product> getAllProductsInCatalog() {
        return productDao.findAll();
    }

    @Override
    public Product getProductFromCatalogById(Integer id) {
        Optional<Product> opt = productDao.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public List<Product> getProductByNameInCatalog(String ProductName) {
        return productDao.findByProductNameContainingIgnoreCase(ProductName);
    }

    @Override
    public List<Product> getProductsByPriceRangeInCatalog(Double minPrice, Double maxPrice) {
        return productDao.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> getProductsByCategoryInCatalog(CategoryEnum category) {
        return productDao.findByCategory(category);
    }


}
