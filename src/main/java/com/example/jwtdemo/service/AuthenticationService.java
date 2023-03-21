package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public User findByUserName(String username) {
        if (username.equals("duongdd")) {
            return new User("duongdd", "1414");
        }

        throw new RuntimeException("User not found");
    }

    public User validateUser(User user) {
        if (user.getUsername().equals("duongdd") && user.getPassword().equals("1414")) {
            return user;
        }
        throw new RuntimeException("User not valid");
    }
}
