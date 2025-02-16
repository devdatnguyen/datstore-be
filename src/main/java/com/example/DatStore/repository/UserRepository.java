package com.example.DatStore.repository;

import com.example.DatStore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);
    Page<User> findByAddressContaining(String address, Pageable pageable);
    Page<User> findByPhoneNumberContaining(String phoneNumber, Pageable pageable);
    Page<User> findByRoleContaining(String role, Pageable pageable);
}
