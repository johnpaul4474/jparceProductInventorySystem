package com.jparce.ProductInventorySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jparce.ProductInventorySystem.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}