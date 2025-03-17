package org.beatrice.hui.controller;

import org.beatrice.hui.model.User;
import org.beatrice.hui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }





    @PostMapping("/api/auth/signup")
    public String handleRegistration(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String city,
                                     @RequestParam String password) throws Exception {
        User user = new User();
        user.setPassword(password);
        user.setCity(city);
        user.setName(name);
        user.setEmail(email);

        userService.registerUser(user);

        return "Success";
    }
}



