package com.example.DatStore.mapper;

import com.example.DatStore.dto.UserDTO;
import com.example.DatStore.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    List<UserDTO> userListToUserDTOList(List<User> userList);
}
