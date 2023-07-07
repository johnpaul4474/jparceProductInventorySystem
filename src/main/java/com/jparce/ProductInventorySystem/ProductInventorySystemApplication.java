package com.jparce.ProductInventorySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.jparce.ProductInventorySystem")
public class ProductInventorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductInventorySystemApplication.class, args);
	}

}
