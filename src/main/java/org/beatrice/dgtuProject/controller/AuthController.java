package org.beatrice.hui.controller;

import org.beatrice.hui.dto.LoginRequest;
import org.beatrice.hui.model.User;
import org.beatrice.hui.repository.UserRepository;
import org.beatrice.hui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    UserService userService;
    UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> handleRegistration(@RequestBody User user) throws Exception {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Stated email is already registered");
            }
            userService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred while registering the user");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}


