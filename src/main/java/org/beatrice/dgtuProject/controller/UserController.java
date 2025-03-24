package org.beatrice.dgtuProject.controller;

import org.beatrice.dgtuProject.dto.ErrorResponse;
import org.beatrice.dgtuProject.model.User;
import org.beatrice.dgtuProject.service.AuthService;
import org.beatrice.dgtuProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestHeader(value = "Authorization", required = false) String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401 UNAUTHORIZED", "Token is missing"));
        }

        String token = authToken.substring(7);
        User user = authService.getUserFromToken(token);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);

    }
}
