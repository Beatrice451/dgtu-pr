package org.beatrice.dgtuProject.controller;

import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.dto.LoginRequest;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.repository.UserRepository;
import org.beatrice.dgtuProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> handleRegistration(@RequestBody User user) throws Exception {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("409 Conflict", "Email is already in use"));
            }
            userService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("500 Internal Server Error", "Everything broke"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}


