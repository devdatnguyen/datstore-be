package com.example.DatStore.controller;

import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.dto.ProductDTO;
import com.example.DatStore.entity.Product;
import com.example.DatStore.mapper.ProductMapper;
import com.example.DatStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;


    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @ModelAttribute FilterDTO filterDTO,
            @RequestParam(defaultValue = "false") boolean sortBySales,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(filterDTO, pageable, sortBySales));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProduct) {
        try {
            ProductDTO product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
