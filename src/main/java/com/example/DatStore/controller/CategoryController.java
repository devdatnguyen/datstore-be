package com.example.DatStore.controller;

import com.example.DatStore.dto.CategoryDTO;
import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.entity.Category;
import com.example.DatStore.exception.ResourceNotFoundException;
import com.example.DatStore.repository.CategoryRepository;
import com.example.DatStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getUsers(
            FilterDTO filterDTO,
            Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategories(filterDTO, pageable));
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO updatedCategory) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category ID not found!");
        }
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
