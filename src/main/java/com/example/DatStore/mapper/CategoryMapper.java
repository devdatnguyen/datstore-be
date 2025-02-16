package com.example.DatStore.mapper;

import com.example.DatStore.dto.CategoryDTO;
import com.example.DatStore.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    List<CategoryDTO> toDto(List<Category> categoryList);
}
