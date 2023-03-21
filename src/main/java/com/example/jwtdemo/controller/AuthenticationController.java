package com.example.jwtdemo.controller;

import com.example.jwtdemo.config.UserAuthenticationProvider;
import com.example.jwtdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserAuthenticationProvider userAuthProvider;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(userAuthProvider.createToken(user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
