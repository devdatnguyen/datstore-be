package com.example.DatStore.service;

import com.example.DatStore.dto.CategoryDTO;
import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryDTO> getAllCategories(FilterDTO filterDTO, Pageable pageable);
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    CategoryDTO updateCategory(Long id, CategoryDTO updatedCategory);
    void deleteCategory(Long id);
}
