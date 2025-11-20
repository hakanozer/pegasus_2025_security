package com.works.config;

import com.works.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    final CustomerService customerService;
    final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests( requests ->
                requests
                        .requestMatchers("/api/v1/product/**").hasRole("product")
                        .requestMatchers("/api/v1/note/**").hasRole("note")
                        .requestMatchers("/api/v1/user/**").hasRole("user")
                        .requestMatchers("/api/v1/customer/**").permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}

/*
* Ali -> user, product
* Veli -> user, note
* Zehra -> user, product, note
* Mehmet -> user
* */