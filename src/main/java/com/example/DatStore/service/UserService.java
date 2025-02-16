package com.example.DatStore.service;

import com.example.DatStore.dto.ChangePasswordRequest;
import com.example.DatStore.dto.UserDTO;
import com.example.DatStore.dto.FilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    void registerUser(UserDTO userDTO);
    UserDTO getUserDTOByUsername(String username);
    boolean checkUsername(String username);
    boolean checkEmail(String email);
    UserDTO getUserById(long id);
    void changePassword(Long id, ChangePasswordRequest request, UserDetails userDetails);
    void updateUser(Long id, UserDTO userDTO);
    Page<UserDTO> getAllUsers(FilterDTO filterDTO, Pageable pageable);
    String deleteUser(Long id);
}