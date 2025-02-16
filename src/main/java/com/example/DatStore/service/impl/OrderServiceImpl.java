package com.example.DatStore.service.impl;

import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.dto.OrderDTO;
import com.example.DatStore.dto.OrderItemDTO;
import com.example.DatStore.dto.ProductDTO;
import com.example.DatStore.entity.Order;
import com.example.DatStore.entity.OrderItem;
import com.example.DatStore.entity.OrderStatus;
import com.example.DatStore.entity.User;
import com.example.DatStore.mapper.OrderItemMapper;
import com.example.DatStore.mapper.OrderMapper;
import com.example.DatStore.repository.OrderItemRepository;
import com.example.DatStore.repository.OrderRepository;
import com.example.DatStore.repository.ProductRepository;
import com.example.DatStore.repository.UserRepository;
import com.example.DatStore.service.OrderItemService;
import com.example.DatStore.service.OrderService;
import com.example.DatStore.service.ProductService;
import com.example.DatStore.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Page<OrderDTO> getOrders(UserDetails userDetails, FilterDTO filterDTO, Pageable pageable) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Page<Order> orders;

        if (user.getRole().toString().equals("ROLE_ADMIN")) {
            orders = searchOrders(filterDTO, pageable);
        } else {
            orders = searchOrdersForUser(user.getId(), filterDTO, pageable);
        }

        return orders.map(orderMapper::toDto);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        try{
            User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
            Order order = orderMapper.toEntity(orderDTO);
            order.setOrderItems(null);
            order.setUser(user);

            Order savedOrder = orderRepository.save(order);
            List<OrderItem> orderItems = new ArrayList<>();
            List<OrderItemDTO> orderItemsDTO = orderDTO.getOrderItems();

            for (OrderItemDTO orderItemDTO : orderItemsDTO) {
                OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(productRepository.findById(orderItemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Not found Product ID: " + orderItemDTO.getProductId())));
                orderItems.add(orderItem);
            }
            orderItemRepository.saveAll(orderItems);

            return savedOrder;
        } catch (Exception e) {
            System.err.println("Error creating order!");
            throw e;
        }
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(updatedOrder.getStatus());
            order.setTotalPrice(updatedOrder.getTotalPrice());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private Page<Order> searchOrders(FilterDTO filterDTO, Pageable pageable) {
        String field = filterDTO.getField();
        String value = filterDTO.getValue();

        switch (field) {
            case "id":
                return orderRepository.findById(Long.parseLong(value),pageable);
            case "username":
                return orderRepository.findByUser_UsernameContainingIgnoreCase(value, pageable);

            case "email":
                return orderRepository.findByUser_EmailContainingIgnoreCase(value, pageable);

            case "phoneNumber":
                return orderRepository.findByUser_PhoneNumberContaining(value, pageable);

            case "address":
                return orderRepository.findByAddressContainingIgnoreCase(value, pageable);

            case "status":
                return orderRepository.findByStatus(OrderStatus.valueOf(value), pageable);

            case "orderDate":
                try {
                    String[] dates = value.split(",");
                    if (dates.length != 2) throw new IllegalArgumentException("Invalid date range!");

                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                    LocalDateTime fromDate = LocalDateTime.parse(dates[0], formatter);
                    LocalDateTime toDate = LocalDateTime.parse(dates[1], formatter);

                    return orderRepository.findByOrderDateBetween(fromDate, toDate, pageable);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format! Expected ISO-8601 (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')", e);
                }

            default:
                throw new IllegalArgumentException("Invalid search field: " + field);
        }
    }

    private Page<Order> searchOrdersForUser(Long userId, FilterDTO filterDTO, Pageable pageable) {
        String field = filterDTO.getField();
        String value = filterDTO.getValue();

        if(field == null || value == null) return orderRepository.findByUserId(userId, pageable);;


        switch (field) {
            case "status":
                return orderRepository.findByUserIdAndStatus(userId, OrderStatus.valueOf(value), pageable);

            case "orderDate":
                String[] dates = value.split(",");
                if (dates.length != 2) throw new IllegalArgumentException("Invalid date range!");
                LocalDateTime fromDate = LocalDateTime.parse(dates[0]);
                LocalDateTime toDate = LocalDateTime.parse(dates[1]);
                return orderRepository.findByUserIdAndOrderDateBetween(userId, fromDate, toDate, pageable);

            default:
                return orderRepository.findByUserId(userId, pageable);
        }
    }

}
