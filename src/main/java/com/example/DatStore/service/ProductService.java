package com.example.DatStore.service;

import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.dto.ProductDTO;
import com.example.DatStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByIds(List<Long> ids);
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO product);
    ProductDTO updateProduct(Long id, ProductDTO updatedProduct);
    void deleteProduct(Long id);
    Page<ProductDTO> getAllProducts(FilterDTO filterDTO, Pageable pageable, boolean sortBySales);
}
