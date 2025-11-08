package com.example.tcs.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    // Use the given Secret Key which is provided in the problem statement
    public static final String SECRET = "67890875646789e9876545906765789";

    public static final long JWT_TOKEN_VALIDITY = 0;

    public String extractUsername(String token) {
        return null;
    }

    public Date extractExpiration(String token) {
        return null;
    }

//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        return null;
//    }

//    private Claims extractAllClaims(String token) {
//        return null;
//    }

    private Boolean isTokenExpired(String token) {
        return null;
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        return null;
//    }

    public String generateToken(String username) {
        return null;
    }

    private String createToken(Map<String, Object> claims, String username) {
        return null;
    }

//    private Key getSignKey() {
//        return null;
//    }
}
