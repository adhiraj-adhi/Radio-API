package com.example.tcs.controllers;

import com.example.tcs.security.AuthRequest;
import com.example.tcs.security.UserDetailServiceImpl;
import com.example.tcs.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager; // We were given this in template

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword());
            Authentication authenticated = authenticationManager.authenticate(toAuthenticate);
            if (authenticated==null) return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
            String token = jwtService.generateToken(authenticated.getName());
            Map<String, String> response = new HashMap<>();
            response.put("username", authenticated.getName());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}