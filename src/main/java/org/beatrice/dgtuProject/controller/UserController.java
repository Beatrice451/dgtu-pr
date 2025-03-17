package org.beatrice.dgtuProject.controller;

import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestHeader(value = "Authorization", required = false) String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 Unauthorized", "Token is missing"));
        }

        String token = authToken.substring(7);
        User user = authService.getUserFromToken(token);
        return ResponseEntity.ok(user);
    }
}
