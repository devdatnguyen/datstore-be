package com.example.DatStore.service.impl;

import com.example.DatStore.dto.ChangePasswordRequest;
import com.example.DatStore.dto.UserDTO;
import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.entity.Role;
import com.example.DatStore.entity.User;
import com.example.DatStore.mapper.UserMapper;
import com.example.DatStore.repository.UserRepository;
import com.example.DatStore.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setAddress(userDTO.getAddress());

        if(userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(String.valueOf(userDTO.getPhoneNumber()));
        }
        user.setEnabled(true);
        user.setRole(userDTO.getRole()== null? Role.ROLE_USER : userDTO.getRole());
        // Pass default
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()==null? "123":userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getName(), user.getEmail(), "", user.getUsername()
                                , user.getRole(),user.getPhoneNumber(),user.getAddress(), user.getId());
    }

    @Override
    public UserDTO getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return userMapper.userToUserDTO(user.orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public Page<UserDTO> getAllUsers(FilterDTO filterDTO, Pageable pageable) {

        String field = filterDTO.getField();
        String value = filterDTO.getValue();
        if (value ==null)
            return userRepository.findAll(pageable)
                    .map(userMapper::userToUserDTO);

        return switch (field) {
            case "username" -> userRepository.findByUsernameContaining(value, pageable).map(userMapper::userToUserDTO);
            case "email" -> userRepository.findByEmailContaining(value, pageable).map(userMapper::userToUserDTO);
            case "name" -> userRepository.findByNameContaining(value, pageable).map(userMapper::userToUserDTO);
            case "address" -> userRepository.findByAddressContaining(value, pageable).map(userMapper::userToUserDTO);
            case "phoneNumber" -> userRepository.findByPhoneNumberContaining(value, pageable).map(userMapper::userToUserDTO);
            case "role" -> userRepository.findByRoleContaining(value, pageable).map(userMapper::userToUserDTO);
            default -> throw new IllegalArgumentException("Field not correct");
        };

    }

    public void changePassword(Long id, ChangePasswordRequest request, UserDetails userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("Not correct User!");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Not correct old Password!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    public boolean checkUsername(String username) {
        username = username.toLowerCase();
        Optional<User> users = userRepository.findByUsername(username);
        return users.isPresent();
    }

    public boolean checkEmail(String email) {
        email = email.toLowerCase();
        Optional<User> users = userRepository.findByEmail(email);
        return users.isPresent();
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole()== null? Role.ROLE_USER : userDTO.getRole());

        userRepository.save(user);
    }
}