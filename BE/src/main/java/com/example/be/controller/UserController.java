package com.example.be.controller;
import com.example.be.model.User;
import com.example.be.notFound.BadRequestException;
import com.example.be.notFound.NotFoundException;
import com.example.be.repository.UserRepository;
import com.example.be.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    public User addUser(@RequestBody User user) {
        // Gọi phương thức thêm người dùng trong UserService
        return userService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        // Gọi phương thức xóa người dùng trong UserService
        userService.deleteUser(userId);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {

        return userService.updateUser(user);
    }

    @GetMapping("/list")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

}
