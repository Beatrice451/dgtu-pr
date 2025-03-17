package org.beatrice.hui.controller;

import org.beatrice.hui.model.User;
import org.beatrice.hui.service.AuthService;
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
    public ResponseEntity<User> getMe(@RequestHeader("Authorization") String authToken) {
        String token = authToken.substring(7);
        User user = authService.getUserFromToken(token);

        return ResponseEntity.ok(user);
    }
}
