package com.example.DatStore.mapper;


import com.example.DatStore.dto.ProductDTO;
import com.example.DatStore.entity.Category;
import com.example.DatStore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "product.category.name", target = "categoryName")
    @Mapping(source = "product.category.id", target = "categoryId")
    ProductDTO toProductDTO(Product product);
    List<ProductDTO> toProductDTOList(List<Product> products);

    @Mapping(target = "category", expression = "java(mapCategory(productDTO.getCategoryId()))")
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID của Product khi update
    @Mapping(target = "category", expression = "java(mapCategory(updatedProduct.getCategoryId()))")
    void updateEntityFromDto(ProductDTO updatedProduct, @MappingTarget Product product);

    default Category mapCategory(String categoryId) {
        if (categoryId == null) return null;
        Category category = new Category();
        category.setId(Long.valueOf(categoryId));
        return category;
    }
}
