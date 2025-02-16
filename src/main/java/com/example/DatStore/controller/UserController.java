package com.example.DatStore.controller;

import com.example.DatStore.dto.ChangePasswordRequest;
import com.example.DatStore.dto.UserDTO;
import com.example.DatStore.dto.FilterDTO;
import com.example.DatStore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getUsers(
            FilterDTO filterDTO,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(filterDTO, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping()
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if(userService.checkEmail(userDTO.getEmail()) || userService.checkUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body("UserName or Email already in use");
        }
        userService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            log.error("Not found user from Principal!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not found user");
        }

        UserDTO user = userService.getUserDTOByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @Validated @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in!");
        }
        userService.changePassword(id, request, userDetails);
        return ResponseEntity.ok("Change password successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String result = userService.deleteUser(id);
        return ResponseEntity.ok(result);
    }
}