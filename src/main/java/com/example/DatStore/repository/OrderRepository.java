package com.example.DatStore.repository;

import com.example.DatStore.entity.Order;
import com.example.DatStore.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Page<Order> findByUser_UsernameContainingIgnoreCase(String username, Pageable pageable);
    Page<Order> findByUser_EmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Order> findByUser_PhoneNumberContaining(String phoneNumber, Pageable pageable);
    Page<Order> findByAddressContainingIgnoreCase(String address, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
    Page<Order> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);
    Page<Order> findByUserIdAndOrderDateBetween(Long userId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    Page<Order> findById(long id, Pageable pageable);
}