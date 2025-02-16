package com.example.DatStore.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);
    String generateRefreshToken(String username);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
}
