package com.example.jwtdemo.controller;

import com.example.jwtdemo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoAPIController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok("Hello " + user.getUsername());
    }
}
