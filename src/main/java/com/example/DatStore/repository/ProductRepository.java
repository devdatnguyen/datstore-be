package com.example.DatStore.repository;

import com.example.DatStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p LEFT JOIN OrderItem od ON p.id = od.product.id " +
           "GROUP BY p " +
           "ORDER BY SUM(od.quantity) DESC NULLS LAST")
    Page<Product> findAllOrderBySales(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByDescriptionContaining(String description, Pageable pageable);
    Page<Product> findByCategoryNameContaining(String categoryName, Pageable pageable);
}
