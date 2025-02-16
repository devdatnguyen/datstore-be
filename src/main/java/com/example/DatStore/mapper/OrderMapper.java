package com.example.DatStore.mapper;

import com.example.DatStore.dto.OrderDTO;
import com.example.DatStore.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO orderDto);
}
