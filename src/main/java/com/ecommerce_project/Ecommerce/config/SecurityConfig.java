package com.ecommerce_project.Ecommerce.config;

import com.ecommerce_project.Ecommerce.security.JwtAuthenticationFilter;
import com.ecommerce_project.Ecommerce.security.JwtUtil;
import com.ecommerce_project.Ecommerce.service.CustomUserDetailsService;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**").permitAll() // Public endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin endpoints
                        .requestMatchers("/user/**").hasRole("USER") // User endpoints
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                new AuthenticationManagerBuilder(http.getSharedObject(BeanFactory.class));
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }
}
