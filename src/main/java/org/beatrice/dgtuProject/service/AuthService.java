package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // Method to get the user from the JWT token
    public User getUserFromToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
