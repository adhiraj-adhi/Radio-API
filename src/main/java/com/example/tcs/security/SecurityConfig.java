package com.example.tcs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import static org.springframework.security.config.Customizer.withDefaults;

@Service
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* Since using this for REST APIs (e.g. testing with Postman), disabling CSRF to
           avoid 403 errors on POST/PUT/DELETE
         */
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/login", "/station/list", "/show/list").permitAll()
                .requestMatchers("/show/get/airing/*", "/show/popularShow").hasRole("USER")
                .requestMatchers("/station/add", "/station/update/*", "/show/add").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}