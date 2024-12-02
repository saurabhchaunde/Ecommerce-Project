package com.Ecommerce.Ecommerce1.Controller;

import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
import com.Ecommerce.Ecommerce1.Service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
        category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();
    }

    @Test
    void createCategory() throws Exception {
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void getCategoryById() throws Exception {
        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.of(category));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));

        verify(categoryService, times(1)).getCategoryById(anyInt());
    }

    @Test
    void getCategoryById_NotFound() throws Exception {
        when(categoryService.getCategoryById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(anyInt());
    }

    @Test
    void getAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(category.getId()))
                .andExpect(jsonPath("$[0].name").value(category.getName()));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllCategories_EmptyList() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void updateCategory() throws Exception {
        when(categoryService.updateCategory(anyInt(), any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));

        verify(categoryService, times(1)).updateCategory(anyInt(), any(Category.class));
    }

    @Test
    void updateCategory_NotFound() throws Exception {
        when(categoryService.updateCategory(anyInt(), any(Category.class))).thenThrow(new CategoryNotFoundException("Category not found with ID: 1"));

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).updateCategory(anyInt(), any(Category.class));
    }

    @Test
    void deleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyInt());

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(anyInt());
    }

    @Test
    void deleteCategory_NotFound() throws Exception {
        doThrow(new CategoryNotFoundException("Category not found with ID: 1")).when(categoryService).deleteCategory(anyInt());

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).deleteCategory(anyInt());
    }
}
