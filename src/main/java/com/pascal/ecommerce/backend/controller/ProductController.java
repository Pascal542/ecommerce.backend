package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.enums.CategoryEnum;
import com.pascal.ecommerce.backend.models.Product;
import com.pascal.ecommerce.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String HomeController() {
        return "Ecommerce Backend";
    }

    // AGREGA UN PRODUCTO
    @PostMapping("/products")
    public ResponseEntity<Product> addProductToCatalog(
            @Valid @RequestBody Product product
    ) {
        Product prod = productService.addProductToCatalog(product);

        return new ResponseEntity<Product>(prod, HttpStatus.ACCEPTED);
    }

    // OBTIENE TODOS LOS PRODUCTOS
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = productService.getAllProductsInCatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    // OBTIENE UN PRODUCTO POR ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductFromCatalogById(
            @PathVariable("id") Integer id
    ) {
        Product prod = productService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(prod, HttpStatus.FOUND);
    }

    // OBTIENE UN PRODUCTO POR NOMBRE
    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam("name") String productName) {
        return productService.getProductByNameInCatalog(productName);
    }

    // OBTIENE PRODUCTOS POR RANGO DE PRECIO
    @GetMapping("/products/price")
    public List<Product> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return productService.getProductsByPriceRangeInCatalog(minPrice, maxPrice);
    }

    // OBTIENE PRODUCTOS POR CATEGORIA
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable CategoryEnum category) {
        List<Product> products = productService.getProductsByCategoryInCatalog(category);
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
}
