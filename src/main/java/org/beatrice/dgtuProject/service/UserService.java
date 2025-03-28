package org.beatrice.dgtuProject.service;


import org.beatrice.dgtuProject.dto.AuthResponse;
import org.beatrice.dgtuProject.dto.LoginRequest;
import org.beatrice.dgtuProject.dto.UserRequest;
import org.beatrice.dgtuProject.exception.*;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void registerUser(UserRequest request) {
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User already exists: " + request.email());
                });

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setCity(request.city());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.email());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse("User created successfully", token);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(user.getId());
    }

    public User getUserInfo(String header) {
        if (header == null || header.isEmpty()) {
            throw new MissingTokenException("Token is missing");
        }

        String token = jwtUtil.getTokenFromHeader(header);
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException("Token is invalid or expired");
        }

        String email = jwtUtil.getEmailFromToken(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


}
