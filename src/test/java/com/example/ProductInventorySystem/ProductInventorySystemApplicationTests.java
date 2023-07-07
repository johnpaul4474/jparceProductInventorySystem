package com.example.ProductInventorySystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jparce.ProductInventorySystem.ProductInventorySystemApplication;
import com.jparce.ProductInventorySystem.model.Product;
import com.jparce.ProductInventorySystem.service.ProductService;

@SpringBootTest(classes = ProductInventorySystemApplication.class)
@AutoConfigureMockMvc
class ProductInventorySystemApplicationTests {

	@Autowired
    private MockMvc mockMvc;
    
	@Autowired
	private ProductService productService;
	 
	@Test
	void contextLoads() {
	}

    @Test
    public void testGetAllProducts() throws Exception {
        // Perform GET request to the base URL
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }
    
    @Test
    public void testProductExists() throws Exception {
        Product product = new Product();
        
        product.setProductName("Test Product");
        product.setProductDescription("Test Description");
        product.setProductType("FOOD");
        product.setProductQuantity(5);
        product.setUnitPrice(5.1);
        productService.createProduct(product);

        Product createProduct = productService.createProduct(product);
        Long productId = createProduct.getProductId();        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product/{id}", productId))
        		.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productDescription").value("Test Description"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.productType").value("FOOD"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.productQuantity").value(5))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.unitPrice").value(5.1));
    }
    public static String asJsonString(final Object obj) {
        try {
          return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    
    @Test
    public void testProductUpdate() throws Exception {        
        Long productId = 2L;
        
        // Perform PUT request to update the product
        String updatedProductName = "Updated Product";
        String updatedProductDescription = "Updated Description";
        String updatedProductType = "FOOD";
        Integer updatedProductQuantity = 10;
        Double updatedUnitPrice = 100.0;
        Product updatedProduct = new Product(productId, updatedProductName, updatedProductDescription,updatedProductType,updatedProductQuantity,updatedUnitPrice);

        
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/product/updateProduct/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional<Product> retrievedProduct = productService.getProductById(productId);
        
        assertEquals(updatedProductName, retrievedProduct.get().getProductName());
        
    }
    
    @Test
    public void testProductUpdateWithNull() throws Exception {        
        Long productId = 2L;
        
        // Perform PUT request to update the product
        String updatedProductName = "With Null";
        String updatedProductDescription = null;
        String updatedProductType = null;
        Integer updatedProductQuantity = 0;
        Double updatedUnitPrice = 0.0;
        Product updatedProduct = new Product(productId, updatedProductName, updatedProductDescription,updatedProductType,updatedProductQuantity,updatedUnitPrice);

        
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/product/updateProduct/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional<Product> retrievedProduct = productService.getProductById(productId);
        
        assertEquals(updatedProductName, retrievedProduct.get().getProductName());
        assertNotNull(updatedProductDescription,retrievedProduct.get().getProductDescription());
        assertNotNull(updatedProductType,retrievedProduct.get().getProductType());
        assertNotEquals(0,retrievedProduct.get().getProductQuantity());
        assertNotEquals(0.0,retrievedProduct.get().getUnitPrice());
    }

}
