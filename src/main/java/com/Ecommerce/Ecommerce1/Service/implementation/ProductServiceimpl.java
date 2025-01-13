package com.Ecommerce.Ecommerce1.Service.implementation;

import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.CategoryRepository;
import com.Ecommerce.Ecommerce1.Repository.ProductRepository;
import com.Ecommerce.Ecommerce1.Service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();

            // Set the category based on the ID provided in the request
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

            updatedProduct.setCategory(category);
            updatedProduct.setName(product.getName());
            updatedProduct.setBrand(product.getBrand());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setDiscount(product.getDiscount());

            // Save and return the updated product
            return productRepository.save(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }


//    @Override
//    public Product updateProduct(int id, Product product) {
//        if (!productRepository.existsById(id)) {
//            throw new ProductNotFoundException("Product not found with ID: " + id);
//        }
//        product.setId(id);
//        return productRepository.save(product);
//    }


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

    @Override
    public List<Product> getProdutsByCategory(int catid) {
        List<Product> Allproducts=productRepository.findAll();
        List<Product> filteredProducts = Allproducts.stream()
                .filter(product -> product.getCategory().getId() == catid)
                .collect(Collectors.toList());

               return filteredProducts;

    }
}
