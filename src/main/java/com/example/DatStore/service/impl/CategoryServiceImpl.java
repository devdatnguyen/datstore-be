package com.example.DatStore.service.impl;

import com.example.DatStore.dto.CategoryDTO;
import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.entity.Category;
import com.example.DatStore.mapper.CategoryMapper;
import com.example.DatStore.repository.CategoryRepository;
import com.example.DatStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDTO> getAllCategories(FilterDTO filterDTO, Pageable pageable) {
        String field = filterDTO.getField();
        String value = filterDTO.getValue();

        if (value ==null)
            return categoryRepository.findAll(pageable)
                    .map(categoryMapper::toDto);

        return switch (field) {
            case "name" -> categoryRepository.findByNameContainingIgnoreCase(value, pageable).map(categoryMapper::toDto);
            default -> throw new IllegalArgumentException("Field not correct");
        };
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryMapper.updateCategoryFromDTO(updatedCategory, category);
                    return categoryRepository.save(category);
                })
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }


    @Override
    public void deleteCategory(Long id) {

        categoryRepository.deleteById(id);
    }
}
