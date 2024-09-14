package com.Perseo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/linkedin", "/api/auth/github").permitAll()
                                .requestMatchers("/orders/create").hasRole("USER")
                                .requestMatchers("/orders/{id}").hasRole("USER")
                                .requestMatchers("/orders/{userId}/add-course").hasRole("USER")
                                .requestMatchers("/experiences").hasRole("USER")
                                .requestMatchers("/experiences/{id}").hasRole("USER")
                                .requestMatchers("/experiences/user/{userId}").hasRole("USER")
                                .requestMatchers("/experiences/{id}/delete").hasRole("USER")
                                .requestMatchers("/courses/add").hasRole("ADMIN")
                                .requestMatchers("/courses/update/{id}").hasRole("ADMIN")
                                .requestMatchers("/courses/delete/{id}").hasRole("ADMIN")
                                .requestMatchers("/courses/{id}").permitAll()
                                .requestMatchers("/courses/all").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/403")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }
}
