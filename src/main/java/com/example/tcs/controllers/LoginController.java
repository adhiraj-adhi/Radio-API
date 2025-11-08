package com.example.tcs.controllers;

import com.example.tcs.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private JwtService jwtService;
//    private AuthenticationManager authenticationManager;

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest) {
//        return null;
//    }
}
