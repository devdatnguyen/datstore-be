package com.example.DatStore.service.impl;

import com.example.DatStore.dto.UserDTO;
import com.example.DatStore.service.JwtService;
import com.example.DatStore.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtServiceImpl(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generateToken(String username) {
        return generateJwtToken(username, 3600, true);
    }

    @Override
    public String generateRefreshToken(String username) {
        return generateJwtToken(username, 604800, false);
    }

    private String generateJwtToken(String username, long expiryInSeconds, boolean includeRole) {
        Instant now = Instant.now();
        UserDTO user = userService.getUserDTOByUsername(username);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .subject(username)
                .claim("id", user.getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryInSeconds));

        if (includeRole) {
            claimsBuilder.claim("role", user.getRole().toString());
        }

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsBuilder.build())).getTokenValue();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            JsonNode payload = decodePayload(parts[1]);
            long exp = payload.get("exp").asLong();
            if (exp < System.currentTimeMillis() / 1000) return false; // Token hết hạn

            String expectedSignature = hmacSha256(parts[0] + "." + parts[1], SECRET_KEY);
            return parts[2].equals(expectedSignature);

        } catch (Exception e) {
            System.err.println("Lỗi validate token: " + e.getMessage());
            return false;
        }
    }


    private JsonNode decodePayload(String base64Payload) throws Exception {
        String payloadJson = new String(Base64.getUrlDecoder().decode(base64Payload));
        return objectMapper.readTree(payloadJson);
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) throw new IllegalArgumentException("Token không hợp lệ");

            JsonNode payload = decodePayload(parts[1]);
            return payload.has("sub") ? payload.get("sub").asText() : null;

        } catch (Exception e) {
            System.err.println("Lỗi khi giải mã token: " + e.getMessage());
            return null;
        }
    }

    private static String hmacSha256(String data, String secret) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal(data.getBytes()));
    }
}