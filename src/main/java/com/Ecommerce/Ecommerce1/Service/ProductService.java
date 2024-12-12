package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    Product createProduct(int categoryId, Product product);
    Product updateProduct(int id,Product product);
    void deleteProduct(int id);
    Optional<Product> getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getProdutsByCategory(int catid);
}
