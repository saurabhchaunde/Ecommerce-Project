package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(int id);
    List<Category> getAllCategories();
    Category updateCategory(int id, Category category);
    void deleteCategory(int id);
}
