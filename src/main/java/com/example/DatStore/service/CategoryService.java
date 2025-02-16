package com.example.DatStore.service;

import com.example.DatStore.dto.CategoryDTO;
import com.example.DatStore.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category updatedCategory);
    void deleteCategory(Long id);
}
