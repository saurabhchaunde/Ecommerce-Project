package com.Ecommerce.Ecommerce1.Controller;

import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
        product = Product.builder()
                .id(1)
                .name("Laptop")
                .brand("Dell")
                .Description("A powerful laptop")
                .price(1000)
                .discount(10)
                .build();
    }

    @Test
    void createProduct() throws Exception {
        when(productService.createProduct(anyInt(), any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).createProduct(anyInt(), any(Product.class));
    }

    @Test
    void createProduct_BadRequest() throws Exception {
        when(productService.createProduct(anyInt(), any(Product.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/products/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());

        verify(productService, times(1)).createProduct(anyInt(), any(Product.class));
    }

    @Test
    void updateProduct() throws Exception {
        when(productService.updateProduct(anyInt(), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).updateProduct(anyInt(), any(Product.class));
    }

    @Test
    void updateProduct_NotFound() throws Exception {
        when(productService.updateProduct(anyInt(), any(Product.class))).thenThrow(new ProductNotFoundException("Product not found with ID: 1"));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).updateProduct(anyInt(), any(Product.class));
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyInt());

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(anyInt());
    }

    @Test
    void deleteProduct_NotFound() throws Exception {
        doThrow(new ProductNotFoundException("Product not found with ID: 1")).when(productService).deleteProduct(anyInt());

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).deleteProduct(anyInt());
    }

    @Test
    void getProductById() throws Exception {
        when(productService.getProductById(anyInt())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).getProductById(anyInt());
    }

    @Test
    void getProductById_NotFound() throws Exception {
        when(productService.getProductById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(anyInt());
    }

    @Test
    void getAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product.getId()))
                .andExpect(jsonPath("$[0].name").value(product.getName()));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getAllProducts_EmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productService, times(1)).getAllProducts();
    }
}
