package com.example.jwtdemo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {
    private String secretKey = "secret-key";

    @Autowired
    private AuthenticationService authService;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public String createToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 60000); // 5 minutes

        return JWT.create()
                .withIssuer(username)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT decoded = verifier.verify(token);

            return new UsernamePasswordAuthenticationToken(authService.findByUserName(decoded.getIssuer()), null, Collections.emptyList());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }


    }

    public Authentication validateUser(User user) {
        return new UsernamePasswordAuthenticationToken(authService.validateUser(user), null, Collections.emptyList());
    }
}
