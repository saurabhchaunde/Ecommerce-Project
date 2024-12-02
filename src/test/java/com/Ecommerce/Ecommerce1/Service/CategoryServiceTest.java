package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.CategoryRepository;
import com.Ecommerce.Ecommerce1.Service.implementation.CategoryServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceimpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();
    }

    @Test
    void createCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals(category.getId(), createdCategory.getId());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void getCategoryById() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryService.getCategoryById(1);

        assertTrue(foundCategory.isPresent());
        assertEquals(category.getId(), foundCategory.get().getId());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryService.getCategoryById(1);

        assertFalse(foundCategory.isPresent());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void getAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> categories = categoryService.getAllCategories();

        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void updateCategory() {
        when(categoryRepository.existsById(anyInt())).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(1, category);

        assertNotNull(updatedCategory);
        assertEquals(category.getId(), updatedCategory.getId());
        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateCategory_NotFound() {
        when(categoryRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.updateCategory(1, category);
        });

        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategory() {
        when(categoryRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(anyInt());

        categoryService.deleteCategory(1);

        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory(1);
        });

        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, never()).deleteById(anyInt());
    }
}
