package com.jparce.ProductInventorySystem.service;

import org.springframework.stereotype.Service;

import com.jparce.ProductInventorySystem.model.Product;
import com.jparce.ProductInventorySystem.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
    	Optional<Product> existingProduct = productRepository.findById(id);
    	if(product.getProductName() == null) {
    		product.setProductName(existingProduct.get().getProductName());
    	}
    	if(product.getProductDescription() == null) {    	
    		product.setProductDescription(existingProduct.get().getProductDescription());
    	}
    	if(product.getProductType() == null) {
    		product.setProductType(existingProduct.get().getProductType());
    	}
    	if(product.getProductQuantity() == 0) {
    		product.setProductQuantity(existingProduct.get().getProductQuantity());
    	}
    	if(product.getUnitPrice() == 0.0) {
    		product.setUnitPrice(existingProduct.get().getUnitPrice());
    	}
    	product.setProductId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
