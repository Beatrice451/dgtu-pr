package org.beatrice.dgtuProject.controller;

import org.beatrice.dgtuProject.dto.ApiResponse;
import org.beatrice.dgtuProject.dto.LoginRequest;
import org.beatrice.dgtuProject.dto.UserRequest;
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
    public ResponseEntity<ApiResponse> handleRegistration(@RequestBody UserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User created successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }
}


