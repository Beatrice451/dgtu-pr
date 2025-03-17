package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.AuthResponse;
import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.dto.LoginRequest;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 Unauthorized", "Invalid email or password"));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 Unauthorized", "Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok().body(new AuthResponse("200 Login successful", token));
    }


}
