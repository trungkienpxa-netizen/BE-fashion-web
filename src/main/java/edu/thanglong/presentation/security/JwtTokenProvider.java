package edu.thanglong.presentation.security;

import edu.thanglong.infrastructure.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private Key getKey() {
        return Keys.hmacShaKeyFor(
            jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(String userId, String role) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .claim("type", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs()))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String extractUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public boolean validate(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(
                    jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)
                ))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}