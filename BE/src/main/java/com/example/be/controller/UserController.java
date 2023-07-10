package com.example.be.controller;
import com.example.be.Service.UserService;
import com.example.be.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
private final UserService userService;

private final UserRepository userRepository;
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }

}
