package com.Ecommerce.Ecommerce1.Controller;

import com.Ecommerce.Ecommerce1.Entity.LoginResponse;
import com.Ecommerce.Ecommerce1.Entity.Users;
import com.Ecommerce.Ecommerce1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService service;


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Users user) {
        // Attempt to verify user and generate a token
        String token = service.verify(user);

        if (token.equals("fail")) {
            // If login fails, return a failure response with an appropriate message
            LoginResponse response = new LoginResponse(false, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        LoginResponse response = new LoginResponse(true, token);
        return ResponseEntity.ok(response);

    }

//    @PostMapping("/login")
//    public String login(@RequestBody Users user) {
//
//        return service.verify(user);
//    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dash")
    public String adminDash() {
        return "Welcome to the admin dashboard!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/personaluser")
    public String userPage() {
        return "Welcome to the personal user page!";
    }
}

