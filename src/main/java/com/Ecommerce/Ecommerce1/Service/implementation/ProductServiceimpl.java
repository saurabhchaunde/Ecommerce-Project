package com.Ecommerce.Ecommerce1.Service.implementation;

import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.CategoryRepository;
import com.Ecommerce.Ecommerce1.Repository.ProductRepository;
import com.Ecommerce.Ecommerce1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceimpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Product createProduct(int categoryId, Product product) {
        Optional<Category> optionalCategory=categoryRepository.findById(categoryId);

        Category category = optionalCategory.orElseThrow(() ->
                new CategoryNotFoundException("Category not found with ID: " + categoryId));

        product.setCategory(category);
       return productRepository.save(product);

    }

    @Override
    public Product updateProduct(int id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        product.setId(id);
        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);

    }

    @Override
    public Optional<Product> getProductById(int id) {

        Optional<Product> p=productRepository.findById(id);
        return p;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
