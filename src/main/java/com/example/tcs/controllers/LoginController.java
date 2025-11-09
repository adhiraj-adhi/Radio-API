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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());

            if(!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
                throw new UsernameNotFoundException("Invalid Credentials");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword(), userDetails.getAuthorities());

            String token = "";
            SecurityContextHolder.getContext().setAuthentication(auth);
            token = jwtService.generateToken(auth.getName());

            if (token==null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Map<String, String> response = new HashMap<>();
            response.put("username", auth.getName());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
