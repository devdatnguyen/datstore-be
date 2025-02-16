package com.example.DatStore.service.impl;

import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.dto.ProductDTO;
import com.example.DatStore.entity.Category;
import com.example.DatStore.entity.Product;
import com.example.DatStore.mapper.ProductMapper;
import com.example.DatStore.repository.ProductRepository;
import com.example.DatStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public ProductDTO getProductById(Long id) {
          return   productRepository.findById(id)
                  .map(productMapper::toProductDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updatedProduct) {
        return productRepository.findById(id).map(product -> {
            productMapper.updateEntityFromDto(updatedProduct, product);
            Product savedProduct = productRepository.save(product);
            return productMapper.toProductDTO(savedProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> getAllProducts(FilterDTO filterDTO, Pageable pageable, boolean sortBySales) {
        Page<Product> productPage;

        //Check best seller
        if (sortBySales) {
            return productRepository.findAllOrderBySales(pageable).map(productMapper::toProductDTO);
        } else {
            String field = filterDTO.getField();
            String value = filterDTO.getValue();
            if (value == null)
                return productRepository.findAll(pageable)
                        .map(productMapper::toProductDTO);

            return switch (field) {
                case "name" -> productRepository.findByNameContaining(value, pageable).map(productMapper::toProductDTO);
                case "categoryName" ->
                        productRepository.findByCategoryNameContaining(value, pageable).map(productMapper::toProductDTO);
                default -> throw new IllegalArgumentException("Field not correct");

            };
        }
    }


}