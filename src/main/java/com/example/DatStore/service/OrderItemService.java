package com.example.DatStore.service;

import com.example.DatStore.entity.OrderItem;
import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(Long id);
    OrderItem createOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem);
    void deleteOrderItem(Long id);
}
