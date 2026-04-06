package edu.thanglong.presentation.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getSigningKey() {
        // Tạo SecretKey từ string secret
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateAccessToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        return createToken(claims, userId, 3600000); // 1 hour = 3600000ms
    }
    
    public String generateRefreshToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        return createToken(claims, userId, 86400000); // 24 hours = 86400000ms
    }
    
    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
            .claims(claims)                    
            .subject(subject)                  
            .issuedAt(now)                     
            .expiration(expiryDate)            
            .signWith(getSigningKey())         
            .compact();
    }
    
    public Claims extractAllClaims(String token) {
        return Jwts.parser()                  
            .verifyWith(getSigningKey())       
            .build()
            .parseSignedClaims(token)          
            .getPayload();                     
    }
    
    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }
    
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
    
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}