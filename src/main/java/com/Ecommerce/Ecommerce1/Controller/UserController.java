package com.Ecommerce.Ecommerce1.Controller;

import com.Ecommerce.Ecommerce1.Entity.Users;
import com.Ecommerce.Ecommerce1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService service;


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return service.verify(user);
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }

    @GetMapping("/dash")
    public String adminDash() {
        return "Welcome to the admin dashboard!";
    }

    @GetMapping("/personaluser")
    public String userPage() {
        return "Welcome to the personal user page!";
    }
}

