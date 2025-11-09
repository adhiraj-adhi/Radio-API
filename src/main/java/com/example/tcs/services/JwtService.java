package com.example.tcs.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    // Use the given Secret Key which is provided in the problem statement
    public static final String SECRET = "678908vivjuyu7k=yuych=75646789e9876545906765789";

    public static final long JWT_TOKEN_VALIDITY = 0;

    @Autowired
    private JwtParser jwtParser;

    public String extractUsername(String token) {
        Claims claims = jwtParser
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
//        Claims claims = jwtParser
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getExpiration();


        // Other Way:
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = jwtParser
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
//        Claims claims = jwtParser
//                .parseClaimsJws(token)
//                .getBody();
//
//        Date expiration = claims.getExpiration();
//        return expiration.before(new Date());

        // Other Way:
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + 3000000))
                .signWith(getSignKey())
                .compact();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3000000))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
}

/*
* (Q) What is difference between generateToken() and createToken()?
* - generateToken() -> When we just need a straightforward token — for example,
*                      only storing the username and expiration.
* - createToken() -> Allows you to add extra data (custom claims) to the JWT.
*                    For example:
                     Map<String, Object> claims = new HashMap<>();
                     claims.put("role", "ADMIN");
                     claims.put("email", "user@example.com");
                     String token = createToken(claims, username);
*                    Adds all those claims to the token payload alongside the standard
*                    fields (sub, iat, exp). We can use it when we want to embed extra user
*                    information (roles, permissions, email, etc.) in the token.
*
*
* (Q) extractAllClaims() vs extractClaim()
* - extractAllClaims() → returns all claims from the token.
* - extractClaim() → returns a specific claim using a resolver function (e.g. subject, expiration).
*
* (Q) SECRET.getBytes(StandardCharsets.UTF_8) vs Decoders.BASE64.decode(SECRET)
* - SECRET.getBytes(StandardCharsets.UTF_8): Use when Base64 encoded key (recommended) say like -
*   SECRET = "aHVudGVyMi1hcHAtc2VjdXJlLWtleQ==";
* - Decoders.BASE64.decode(SECRET): Use when we have plain text key (not Base64) say like -
*   SECRET = "mySuperSecretKey12345";
* */