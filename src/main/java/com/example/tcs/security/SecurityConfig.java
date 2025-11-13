package com.example.tcs.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.List;

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
//        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static final String SECRET = "678908vivjuyu7k=yuych=75646789e9876545906765789";
    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET))).build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
        /*
        * It returns Spring Security’s pre-built AuthenticationManager, which already knows:
        * - Which AuthenticationProvider to use → by default, a DaoAuthenticationProvider
        * - Which UserDetailsService to call → our UserDetailServiceImpl (because it’s a
        *   @Service implementing UserDetailsService)
        * - Which PasswordEncoder to use → our @Bean PasswordEncoder (e.g. BCryptPasswordEncoder)
        * - Which database table to check → whatever our UserDetailServiceImpl queries (via
        *   our UserRepository)
        * */
    }
}