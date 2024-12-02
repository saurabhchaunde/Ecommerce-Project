package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Users;
import com.Ecommerce.Ecommerce1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Register user and encode the password
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    // Verify user credentials and generate JWT token
    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            return jwtService.generateToken(userDetails);
        } else {
            return "fail"; // Return failure message if authentication fails
        }
    }
}
