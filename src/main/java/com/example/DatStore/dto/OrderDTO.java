package com.example.DatStore.dto;

import com.example.DatStore.entity.OrderStatus;
import com.example.DatStore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private long userId;
    private LocalDateTime orderDate = LocalDateTime.now();
    private OrderStatus status = OrderStatus.PROCESSING;
    private double totalPrice;
    private List<OrderItemDTO> orderItems;
    private String address;
    private String phoneNumber;
}
