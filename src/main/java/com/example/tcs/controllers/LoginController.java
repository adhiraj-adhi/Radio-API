package com.example.tcs.controllers;

import com.example.tcs.security.AuthRequest;
import com.example.tcs.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest) {
        authenticationManager.authenticate(authRequest);
        return null;
    }
}
