package com.example.jwtdemo.config;

import com.example.jwtdemo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserAuthenticationProvider userAuthProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/api/v1/auth/sign-in".equals(request.getServletPath())
            && HttpMethod.POST.matches(request.getMethod())) {
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            try {
                SecurityContextHolder.getContext().setAuthentication(userAuthProvider.validateUser(user));
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw new RuntimeException("Login Failed!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
