package com.jparce.ProductInventorySystem.controller;

import org.springframework.web.bind.annotation.*;

import com.jparce.ProductInventorySystem.model.Product;
import com.jparce.ProductInventorySystem.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductService productService;
  
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody Product product) {
    	
        return productService.createProduct(product);
    }

    @PutMapping("/updateProduct/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {

    	
        return productService.updateProduct(id,product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}