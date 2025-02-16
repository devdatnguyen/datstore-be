package com.example.DatStore.mapper;

import com.example.DatStore.dto.OrderDTO;
import com.example.DatStore.entity.Order;
import com.example.DatStore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "order.user.id", target = "userId")
    @Mapping(source = "order.user.email", target = "userEmail")
    @Mapping(source = "order.user.username", target = "username")
    OrderDTO toDto(Order order);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUser")
    Order toEntity(OrderDTO orderDto);

    @Named("mapUser")
    default User mapUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}
