package com.example.DatStore.service;

import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.dto.OrderDTO;
import com.example.DatStore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> getOrderById(Long id);
    Order createOrder(OrderDTO order);
    Order updateOrder(Long id, Order updatedOrder);
    void deleteOrder(Long id);
    Page<OrderDTO> getOrders(UserDetails user, FilterDTO filterDTO, Pageable pageable);
}
