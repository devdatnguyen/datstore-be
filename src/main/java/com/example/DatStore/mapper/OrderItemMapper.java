package com.example.DatStore.mapper;

import com.example.DatStore.dto.OrderItemDTO;
import com.example.DatStore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "orderItem.product.id", target = "productId")
    OrderItemDTO toDto(OrderItem orderItem);

    OrderItem toEntity(OrderItemDTO orderItemDtp);

    List<OrderItemDTO> toDto(List<OrderItem> orderItemList);
    List<OrderItem> toEntity(List<OrderItemDTO> orderItemDTOList);
}
