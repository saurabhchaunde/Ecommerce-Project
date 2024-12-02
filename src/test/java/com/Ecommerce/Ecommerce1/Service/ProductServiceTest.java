package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.CategoryRepository;
import com.Ecommerce.Ecommerce1.Repository.ProductRepository;
import com.Ecommerce.Ecommerce1.Service.implementation.ProductServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceimpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        Category category = new Category();
        category.setId(1);
        Product product = new Product();
        product.setName("Test Product");
        product.setCategory(category);

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(1, product);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        verify(categoryRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void createProduct_CategoryNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        Product product = new Product();
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            productService.createProduct(1, product);
        });

        assertEquals("Category not found with ID: 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Updated Product");

        when(productRepository.existsById(anyInt())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(1, product);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).existsById(1);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_ProductNotFound() {
        when(productRepository.existsById(anyInt())).thenReturn(false);

        Product product = new Product();
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1, product);
        });

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).existsById(1);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyInt());

        assertDoesNotThrow(() -> {
            productService.deleteProduct(1);
        });

        verify(productRepository, times(1)).existsById(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProduct_ProductNotFound() {
        when(productRepository.existsById(anyInt())).thenReturn(false);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(1);
        });

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).existsById(1);
        verify(productRepository, never()).deleteById(anyInt());
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId(1);
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.getProductById(1);

        assertTrue(foundProduct.isPresent());
        assertEquals(1, foundProduct.get().getId());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.getProductById(1);

        assertFalse(foundProduct.isPresent());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }
}
